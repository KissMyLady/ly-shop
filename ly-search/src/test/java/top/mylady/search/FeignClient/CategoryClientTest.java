package top.mylady.search.FeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.item.pojo.Category;
import top.mylady.search.client.GoodsClient;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    private static final Logger logger = LoggerFactory.getLogger(CategoryClientTest.class);

    @Autowired
    private GoodsClient goodsClient;

    /**
     *
     */
    @Test
    public void listIds(){
        List<Long> ids = new ArrayList<>();
        ids.add(74L);
        ids.add(75L);
        ids.add(76L);

        List<Category> categories = goodsClient.queryCategoryByIds(ids);

        categories.forEach((cate)->{
            System.out.println("cate: "+ cate);
        });

    }
}
