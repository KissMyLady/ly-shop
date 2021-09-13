package top.mylady.search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.Product;


/**
 * 索引操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 启动此测试类, 用来创建索引并增加映射配置
     * 查询创建的索引: http://116.62.101.170:9200/_cat/indices?v
     */
    @Test
    public void createIndex(){
        try {
            elasticsearchRestTemplate.createIndex(Product.class);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
        }

        // 参考文章: https://blog.csdn.net/weixin_44257023/article/details/110148280
        System.out.println("创建索引成功");
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
