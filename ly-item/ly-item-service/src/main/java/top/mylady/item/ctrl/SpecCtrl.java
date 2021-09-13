package top.mylady.item.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.mylady.item.service.SpecificationService;


@RestController
@RequestMapping("/spec")
public class SpecCtrl {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据id查询分组
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity queryGroupByCid(@PathVariable("cid")Long cid){
        return this.specificationService.queryGroupsByUd(cid);
    }

    /**
     * 规格查询
     */
    @GetMapping("/params")
    public ResponseEntity queryParams(@RequestParam("gid") Long gid){
        return this.specificationService.queryParams(gid);
    }

}
