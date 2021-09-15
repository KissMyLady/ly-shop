package top.mylady.item.ctrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.item.pojo.Category;
import top.mylady.item.service.CategoryService;
import top.mylady.common.utils.dtos.ResponseResult;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryCtrl {

    private static final Logger logger = LoggerFactory.getLogger(CategoryCtrl.class);

    @Autowired
    private CategoryService categoryService;
    //ResponseEntity: 将toString的字符数据转化成json

    @GetMapping("/list")
    public ResponseEntity queryCategory(@RequestParam(value="pid", required = false) Long pid){
        return categoryService.queryCategoryByPid(pid);
    }

    /**
     * 根据商品分类cid列表查询商品分类集合 cid1, cid2, cid3 这样查询
     */
    @GetMapping("/list/ids")
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryCategoryListByPid(ids));
    }

    @GetMapping("/test")
    public ResponseResult testUser(){
        return categoryService.testUser();
    }

    /**
     * 根据id查询分类
     * params: /category/names?ids=1,2,3,4
     * return形式:
     * [ "图书、音像、电子书刊", "电子书刊", ]
     */
    @GetMapping("/names")
    public ResponseEntity queryCategoryName(@RequestParam(value="ids") List<Long> ids){
        return categoryService.queryByIds(ids);
    }

}
