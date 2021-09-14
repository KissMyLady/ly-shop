package top.mylady.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.item.pojo.Brand;
import top.mylady.item.pojo.Category;

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
     * Bug: 原接口这个是有bug, SQL语句有问题; 返回是空
     */
    @GetMapping("category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);

    /**":wq:wqa:
     *:
     * Brand查询
     */
    @GetMapping("brand/{id}")
    Brand queryBrandById(@PathVariable("id")Long id);

    @GetMapping("brand/list")
    List<Brand> queryBrandsByIds(@RequestParam("ids") List<Long> ids);




}
