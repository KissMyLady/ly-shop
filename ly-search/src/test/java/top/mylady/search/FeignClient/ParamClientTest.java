package top.mylady.search.FeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.item.pojo.SpecParam;
import top.mylady.search.client.GoodsClient;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ParamClientTest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryClientTest.class);

    @Autowired
    private GoodsClient goodsClient;


    /**
     * 规格查询
     */
    @Test
    public void paramTest(){
        try {
            List<SpecParam> params = goodsClient.querySpecParams(null, 76L, null);
            System.out.println("params: "+ params);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
        }

    }

}
