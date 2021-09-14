package top.mylady.search.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import top.mylady.common.vo.PageResult;
import top.mylady.item.pojo.Spu;
import top.mylady.search.client.GoodsClient;
import top.mylady.search.pojo.Goods;
import top.mylady.search.repository.GoodsDao;
import top.mylady.search.service.InsertEsService;



/**
 * 商品搜索数据添加到 ElasticSearch
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsDataAdd {

    private static final Logger logger = LoggerFactory.getLogger(GoodsDataAdd.class);

    @Autowired
    private InsertEsService insertEsService;

    @Autowired
    private GoodsClient goodsClient;

    @Resource
    private GoodsDao goodsDao;

    /**
     * 需要启动的服务:
     *    - Register     注册中小
     *    - item-service 商品服务
     */
    @Test
    public void insertDataToEs(){
        Integer rows = 100;
        Integer page = 1;
        logger.debug("开始插入数据到 ElasticSearch >>>>>>>>>>>>");

        do{
            //查询Spu
            PageResult<Spu> pageResult = this.goodsClient.querySpuByPage(page, rows, null, null);
            List<Goods> goodsList = new ArrayList<>();

            //遍历查询到的spu
            pageResult.getItems().forEach( (item) ->{
                try {
                    //执行
                    Goods goods = this.insertEsService.buildGoods((Spu)item);
                    goodsList.add(goods);
                }
                catch (Exception e){
                    logger.warn("执行insertEsService服务错误, 原因是: "+ e);
                }
            });

            //执行
            this.goodsDao.saveAll(goodsList);

            //获取当前分页数据, 如果是最后一条, 没有100条, 结束do-while循环
            rows = pageResult.getItems().size();
            page ++;
        }while (rows == 100);

    }
}
