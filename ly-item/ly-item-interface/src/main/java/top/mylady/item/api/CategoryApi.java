package top.mylady.item.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 商品分类服务类接口
 */
@RequestMapping("/category")
public interface CategoryApi {

    /**
     * 通过ids查询商品分类
     */
    @GetMapping("/names")
    ResponseEntity<List<String>> queryNameByIds(@RequestParam(value="ids") List<Long> ids);

}
