package top.mylady.item.ctrl;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.mylady.item.pojo.Sku;
import top.mylady.item.pojo.Spu;
import top.mylady.item.service.GoodsService;

import java.util.List;


@RestController
public class GoodsCtrl {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询spu
     */
    @GetMapping("/spu/page")
    public ResponseEntity querySpuByPage(
            @RequestParam(value="page", defaultValue="1")Integer page,
            @RequestParam(value="rows", defaultValue="5")Integer rows,
            @RequestParam(value="saleable", required=false)Boolean saleable,
            @RequestParam(value="key", required=false)String key){
        return goodsService.querySpuByPage(page, rows, saleable, key);
    }

    //仅提供思路, 此API未完成
    @PostMapping("/goods")
    public ResponseEntity saveGoods(@RequestBody Spu spu){
        String ok = this.goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * sku查询, 一并查询库存,
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
        return ResponseEntity.ok(goodsService.querySkuBySpuId(id));
    }

}
