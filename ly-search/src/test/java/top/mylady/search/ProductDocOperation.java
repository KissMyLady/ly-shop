package top.mylady.search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.Product;
import top.mylady.search.repository.ProductDao;

import java.util.ArrayList;
import java.util.List;


/**
 * 文档数据的操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDocOperation {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ProductDao productDao;

    /**
     * 索引文档操作
     *   - 新增数据
     *   - 查询 http://116.62.101.170:9200/product/_doc/2
     */
    @Test
    public void newDoc(){
        Product p = new Product();
        p.setId(2L);
        p.setTitle("华为手机");
        p.setCategory("手机");
        p.setPrice(2999.0);
        p.setImages("https://blog.mylady.top/static/images/logo/qian_avatar_1.jpg");
        productDao.save(p);
        System.out.println("操作文档, 添加数据完成");
    }

    /**
     * 批量增加数据
     */
    @Test
    public void saveAll(){
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Product p = new Product();
            p.setId((long) i);
            p.setTitle("苹果手机"+ "[" + i + "]");
            p.setCategory("手机");
            p.setPrice(2999.0+ i* 1000);
            p.setImages("https://blog.mylady.top/static/images/logo/qian_avatar_1.jpg");
            products.add(p);
        }
        productDao.saveAll(products);
        System.out.println("数据批量添加操作完成");
    }

    /**
     * 删除数据
     */
    @Test
    public void deleteById(){
        productDao.deleteById(2L);
    }

    /**
     * 修改数据
     */
    @Test
    public void docChange(){
        Product p = new Product();
        p.setId(2L);
        p.setTitle("苹果手机");

        productDao.save(p);
    }

    /**
     * 查询单条数据
     */
    @Test
    public void docQueryById(){
        Product product = productDao.findById(2L).get();
        System.out.println("操作文档, 查询数据完成, 打印查询到的数据product: "+ product);
    }

    /**
     * 查询所有数据
     */
    @Test
    public void docQueryAll(){
        Iterable<Product> products = productDao.findAll();

        products.forEach( (product -> {
            System.out.println("遍历打印查询到的数据product: "+ product);
        }));
    }


    /**
     * 分页查询
     */
    @Test
    public void findByPageable(){
        //设置排序方法
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int from = 0;
        int size = 5;

        //设置查询分页
        PageRequest pageRequest = PageRequest.of(from, size, sort);

        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);

        productPage.forEach( (product)->{
            System.out.println("forEach遍历打印分页查询的结果product: "+ product);
        });
    }




}
