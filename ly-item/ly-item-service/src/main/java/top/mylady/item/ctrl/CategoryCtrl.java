package top.mylady.item.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.item.service.CategoryService;
import top.mylady.common.utils.dtos.ResponseResult;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryCtrl {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity queryCategory(@RequestParam(value="pid", required = false) Long pid){
        return categoryService.queryCategoryByPid(pid);
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
