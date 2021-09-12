package top.mylady.search;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.Product;
import top.mylady.search.repository.ProductDao;


/**
 * 文档搜索
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductQuery {

    @Autowired
    private ProductDao productDao;


    /**
     * Term条件查询
     */
    @Test
    public void query(){
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");
        Iterable<Product> products = productDao.search(termQueryBuilder);
        products.forEach( (p)->{
            System.out.println("遍历查询到的 标题为苹果的数据: "+ p);
        });
    }

    /**
     * Term加分页查询
     */
    @Test
    public void queryByPage(){
        //设置排序方法
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int from = 0;
        int size = 5;

        //设置查询分页
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("category", "手机");

        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
        products.forEach( (p)->{
            System.out.println("遍历查询到的 标题为苹果的数据: "+ p);
        });
    }

}
