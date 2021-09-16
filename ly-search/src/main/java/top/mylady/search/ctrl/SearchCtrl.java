package top.mylady.search.ctrl;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.mylady.common.vo.PageResult;
import top.mylady.search.client.GoodsClient;
import org.springframework.http.ResponseEntity;
import top.mylady.search.pojo.Goods;
import top.mylady.search.pojo.SearchRequest;
import top.mylady.search.pojo.SearchResult;
import top.mylady.search.service.AggsSearchResult;
import top.mylady.search.service.SearchService;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class SearchCtrl {

    private static final Logger logger = LoggerFactory.getLogger(SearchCtrl.class);

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private AggsSearchResult aggsSearchResult;

    /**
     * 搜索返回数据 page的加强版, 使用聚合查询
     */
    @PostMapping("/pages")
    public ResponseEntity searchPlus(@RequestBody SearchRequest request){
        logger.info("接口的加强版, 使用聚合查询 >>>>>>>>>>>> ");
        SearchResult result = aggsSearchResult.search(request);

        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 普通关键字搜索, 返回搜索结果
     */
    @PostMapping("/page")
    public ResponseEntity search(@RequestBody SearchRequest request){
        PageResult<Goods> result = searchService.search(request);

        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/ok")
    public String test(){
        return "search ping is ok";
    }

    @GetMapping("/ok2")
    public ResponseEntity test2(@RequestParam("ids") List<Long> ids){
        List<String> listStr = goodsClient.queryCategoryName(ids);
        return ResponseEntity.ok(listStr);
    }


}
