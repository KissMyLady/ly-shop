package top.mylady.item.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.mylady.item.pojo.SpecParam;
import top.mylady.item.service.SpecificationService;

import java.util.List;


@RestController
@RequestMapping("/spec")
public class SpecCtrl {

    private static final Logger logger = LoggerFactory.getLogger(SpecCtrl.class);

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
    @GetMapping("/paramss")
    public ResponseEntity queryParams(
            @RequestParam(value="gid", required=true) Long gid,
            @RequestParam(value="cid", required=false) Long cid,
            @RequestParam(value="generic", required=false) Boolean generic,
            @RequestParam(value="searching", required=false) Boolean searching
    ){
        return this.specificationService.queryParams(gid, cid, generic, searching);
    }

    /**
     * 规格查询 升级Plush版
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> querySpecParams(
            @RequestParam(value="gid", required=false)Long gid,
            @RequestParam(value="cid", required=false)Long cid,
            @RequestParam(value="searching", required=false)Boolean searching
    ){
        if ( gid==null && cid==null && searching==null ){
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(specificationService.querySpecParams(gid, cid, searching));
    }

}
