package top.mylady.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.common.vo.PageResult;
import top.mylady.item.pojo.*;

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
     * 查询商品分类
     */
    @GetMapping("/category/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);

    /**
     * Brand查询
     */
    @GetMapping("/brand/{id}")
    Brand queryBrandById(@PathVariable("id")Long id);

    @GetMapping("/brand/list")
    List<Brand> queryBrandsByIds(@RequestParam("ids") List<Long> ids);

    /**
     * 规格查询 升级Plush版
     */
    @GetMapping("/spec/params")
    List<SpecParam> querySpecParams(
            @RequestParam(value="gid",       required=false)Long gid,
            @RequestParam(value="cid",       required=false)Long cid,
            @RequestParam(value="searching", required=false)Boolean searching);

//    @GetMapping("/spec/params")
//    List<SpecParam> querySpecParams(@RequestParam(value="gid",      required=false) Long gid,
//                                    @RequestParam(value="cid",      required=false) Long cid,
//                                    @RequestParam(value="searching",required=false )Boolean searching);

    @GetMapping("/spu/detail/{id}")
    SpuDetail querySpuDetailById(@PathVariable("id")Long id);

    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(
            @RequestParam(value="page",     defaultValue="1") Integer page,
            @RequestParam(value="rows",     defaultValue="5") Integer rows,
            @RequestParam(value="saleable", required=false)   Boolean saleable,
            @RequestParam(value="key",      required=false)   String  key
    );

    @GetMapping("/sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);
}
