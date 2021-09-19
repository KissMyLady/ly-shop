package top.mylady.page.ctrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.page.service.PageService;
import org.springframework.ui.Model;


@Controller
public class PageCtrl {

    @Autowired
    private PageService pageService;

    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id")Long id, Model model){

        //查询数据

        //返回数据

        return "item";

    }
}
