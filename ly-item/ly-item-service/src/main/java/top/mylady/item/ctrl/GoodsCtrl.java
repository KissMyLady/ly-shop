package top.mylady.item.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.mylady.item.pojo.Spu;
import top.mylady.item.service.GoodsService;


@RestController
@RequestMapping("/goods")
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

    @PostMapping("/goods")
    public ResponseEntity saveGoods(@RequestBody Spu spu){
        String ok = this.goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
