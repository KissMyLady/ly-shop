package top.mylady.search.ctrl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search")
public class SearchCtrl {


    @GetMapping("/ok")
    public String test(){
        return "search ping is ok";
    }
}
