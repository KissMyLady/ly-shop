package top.mylady.search.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


/**
 * 动态代理
 */
@FeignClient(value="item-service")
public interface GoodsClient {


    /**
     * 分类查询
     */
    @GetMapping("/category/names")
    public abstract List<String> queryCategoryName(@RequestParam("ids") List<Long> ids);

    /**
     * 品牌查询
     */




}