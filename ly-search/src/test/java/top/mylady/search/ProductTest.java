package top.mylady.search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.Product;


/**
 * SpringData集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建索引并增加映射配置
     */
    @Test
    public void createIndex(){
        elasticsearchRestTemplate.createIndex(Product.class);
        // 参考文章: https://blog.csdn.net/weixin_44257023/article/details/110148280
        System.out.println("创建索引");
    }

    /**
     * 删除所谓
     */
    @Test
    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);
        System.out.println("删除索引 = " + flg);
    }




}
