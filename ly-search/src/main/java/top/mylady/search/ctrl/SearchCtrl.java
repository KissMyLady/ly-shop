package top.mylady.search.ctrl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.mylady.common.vo.PageResult;
import top.mylady.search.client.GoodsClient;
import org.springframework.http.ResponseEntity;
import top.mylady.search.pojo.Goods;
import top.mylady.search.pojo.SearchRequest;
import top.mylady.search.service.SearchService;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class SearchCtrl {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    /**
     * 搜索返回数据
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
