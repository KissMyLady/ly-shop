package top.mylady.item.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.item.pojo.Brand;
import top.mylady.item.service.BrandService;


@RestController
@RequestMapping("/brand")
public class BrandCtrl {

    @Autowired
    private BrandService brandService;

    @GetMapping("/query")
    public ResponseEntity queryById(@RequestParam("id")Long id){
        Brand brand = brandService.queryById(id);
        if (brand ==null){
            return new ResponseEntity<>("数据不存在", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(brand);
    }

    @GetMapping("/ok")
    public String ok(){
        return "ok";
    }
}
