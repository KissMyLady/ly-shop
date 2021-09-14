package top.mylady.search.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.mylady.search.pojo.Goods;


/**
 * 将Mysql 转换到 Es
 */
@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);


    public Goods buildGoods(){

        //1; 创建对象
        Goods goods = new Goods();

        //2; 查询品牌


        return null;
    }

    private String chooseSegment(){
        return "ok";
    }

}
