package top.mylady.search.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.Goods;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建Goods 索引
     */
    @Test
    public void createIndex(){
        try {
            elasticsearchRestTemplate.createIndex(Goods.class);
            elasticsearchRestTemplate.putMapping(Goods.class);
        }
        catch (Exception e){
            System.out.println("创建索引错误, 原因e: "+ e);
        }
        System.out.println("索引创建完成");
    }

    @Test
    public void deleteIndex(){

        boolean flag = elasticsearchRestTemplate.deleteIndex(Goods.class);

        if (flag){
            System.out.println("删除索引成功");
        }
    }


}
