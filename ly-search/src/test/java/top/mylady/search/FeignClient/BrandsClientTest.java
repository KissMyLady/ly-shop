package top.mylady.search.FeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.client.GoodsClient;

import java.util.ArrayList;
import java.util.List;


/**
 * 远程Feign调用测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandsClientTest {

    @Autowired
    private GoodsClient goodsClient;


    @Test
    public void MoteCall() {

        try {
            System.out.println(goodsClient.queryBrandById(1528L));
            List<Long> arr = new ArrayList<>();
            arr.add(1528L);
            arr.add(1912L);

            System.out.println(goodsClient.queryBrandsByIds(arr));
            System.out.println(goodsClient.queryCategoryByIds(arr));

            System.out.println("pass");
        } catch (Exception e) {
            System.out.println("远程调用错误, 原因e: " + e);
        }

    }
}
