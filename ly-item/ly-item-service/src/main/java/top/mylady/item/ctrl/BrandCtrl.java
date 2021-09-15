package top.mylady.item.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import top.mylady.item.pojo.Brand;
import top.mylady.item.service.BrandService;

import java.util.List;


@RestController
@RequestMapping("/brand")
public class BrandCtrl {

    private static final Logger logger = LoggerFactory.getLogger(BrandCtrl.class);

    @Autowired
    private BrandService brandService;

    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.queryById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Brand>> queryBrandsByIds(@RequestParam("ids")List<Long> ids){
        return ResponseEntity.ok(brandService.queryBrandsByIds(ids));
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity queryBrandsByCid(@PathVariable("cid")Long cid){
        List<Brand> brands = this.brandService.queryBrandsByCid(cid);
        if (CollectionUtils.isEmpty(brands)){
            return new ResponseEntity<>("数据不存在", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }
}
