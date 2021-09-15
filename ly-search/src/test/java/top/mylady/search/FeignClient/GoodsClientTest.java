package top.mylady.search.FeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.item.pojo.SpuDetail;
import top.mylady.search.client.GoodsClient;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsClientTest {

    @Autowired
    private GoodsClient goodsClient;
    //querySpuDetailById

    /**
     * Spu详情表的查询
     */
    @Test
    public void spuDetail(){
        SpuDetail spuDetail = goodsClient.querySpuDetailById(10L);
        System.out.println("spuDetail: "+ spuDetail);
    }

}
