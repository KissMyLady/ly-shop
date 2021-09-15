package top.mylady.item.api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.item.pojo.Brand;

import java.util.List;


public interface BrandApi {

    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id")Long id);

    @GetMapping("/brand/list")
    List<Brand> queryBrandsByIds(@RequestParam("ids") List<Long> ids);

}
