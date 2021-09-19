package top.mylady.search.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.search.pojo.SearchRequest;
import top.mylady.search.pojo.SearchResult;
import top.mylady.search.service.AggsSearchResult;


/**
 * 聚合查询测试 渲染
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AggsSearch {

    private static final Logger logger = LoggerFactory.getLogger(AggsSearch.class);

    @Autowired
    private AggsSearchResult aggsSearchResult;

    @Test
    public void aggregationSearch(){
        SearchRequest request = new SearchRequest();
        request.setKey("手机");
        try {
            SearchResult res = aggsSearchResult.search(request);
            logger.info("打印查询到的结果res: "+ res);
        }
        catch (Exception e){
            logger.warn("测试错误, 原因是: "+ e);
        }

    }

}
