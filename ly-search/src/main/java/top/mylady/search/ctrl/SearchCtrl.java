package top.mylady.search.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.search.client.GoodsClient;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/search")
public class SearchCtrl {

    @Autowired
    private GoodsClient goodsClient;

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
