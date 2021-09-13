package top.mylady.search.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(value="item-service")
public interface GoodsClient {

    /**
     * 动态代理
     */
    @GetMapping("/category/names")
    public abstract List<String> queryCategoryName(@RequestParam("ids") List<Long> ids);

}
