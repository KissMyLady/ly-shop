package top.mylady.search.service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.common.utils.JsonUtils;
import top.mylady.common.utils.NumberUtils;
import top.mylady.item.pojo.*;
import top.mylady.search.client.GoodsClient;
import top.mylady.search.pojo.Goods;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 将Mysql 数据插入 Es
 */
@Service
public class InsertEsService {

    private static final Logger logger = LoggerFactory.getLogger(InsertEsService.class);

    @Autowired
    private GoodsClient goodsClient;


    /**
     * 主体插入完成, 能够插入绝大部分数据, 部分bug接下来解决, 共有4处断点需要维护
     * 1, 查询分类
     * 2, 所有sku的集合的json格式 [依赖上面1问题, 1问题解决, 这个就解决了]
     * 3, 查询规格参数  结果是一个map
     * 4, 将参数填入map [依赖3问题]
     */
    public Goods buildGoods(Spu spu){

        //1; 创建对象
        Goods goods = new Goods();

        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        goods.setId(spu.getId());

        //2; 查询分类
        List<String> names = null;
        try {
            names = goodsClient.queryCategoryByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            logger.info("查询分类数据查询成功, 打印names: "+ names);
        }
        catch (Exception e){
            logger.warn("Es调用Item模块查询分类错误, names就不查了, 数值给空, 继续下一步: "+ e);
        }

        //3; 查询品牌
        Brand brand = null;
        try {
            brand = goodsClient.queryBrandById(spu.getBrandId());
            logger.info("查询品牌数据查询成功, 打印brand: "+ brand);  //ok
        }
        catch (Exception e){
            logger.warn("查询品牌插入数据为null,names就不查了, brand数值给空, 继续下一步: "+ e);
        }

        String all;
        if (names == null || brand == null){
            all = null;
            logger.warn("品牌和分类有一个为空, all现在设置为空, 跳过这步 继续");
        }else {
            //4; sku --- 所有sku的集合的json格式
            all = spu.getTitle() + StringUtils.join(names," ") + brand.getName();
            logger.warn("品牌和分类都不为空, 打印拼接的all: "+ all);
        }

        // sku --- 所有sku的集合的json格式
        List<Sku> skuList  = null;
        try {
            skuList = goodsClient.querySkuBySpuId(spu.getId());
            logger.info("所有sku的集合的json格式, skuList不为空, 打印查询到的结果: "+ skuList);  //ok
        }
        catch (Exception e){
            logger.warn("所有sku的集合的json格式, skuList为空, 继续下一步: "+ e);
        }
        // 搜索字段只需要部分数据(id,title,price,image) 所以要对sku进行处理
        ArrayList<Map<String, Object>> skus = new ArrayList<>();

        //Price插入
        Set<Long> priceList = new HashSet<>();
        if (skuList != null){
            for (Sku sku: skuList){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", sku.getId());
                map.put("title",sku.getTitle());
                map.put("price",sku.getPrice());
                map.put("image", StringUtils.substringBefore(sku.getImages(),","));
                skus.add(map);
                priceList.add(sku.getPrice());
            }
        }else {
            logger.warn("警告 Price数据为空, 跳过插入价格数据");
        }

        // 查询规格参数  结果是一个map
        List<SpecParam> params = null;
        try {
            params = goodsClient.querySpecParams(null, spu.getCid3(), true);
            logger.info("查询规格参数  结果是一个map, 能够查询到数据, 打印params: "+ params);
        }
        catch (Exception e){
            logger.warn("查询规格参数  结果是一个map, 数据查询失败: "+ e);  //错误
        }


        // 规格参数表
        SpuDetail spuDetail = null;
        try {
            spuDetail = goodsClient.querySpuDetailById(spu.getId());
            logger.info("规格参数表, 数据不为空, 打印查询到的spuDetail: "+ spuDetail);
        }
        catch (Exception e){
            logger.warn("规格参数表, 数据查询失败, 原因: "+ e);
        }

        Map<Long, String> genericSpec = null;
        Map<Long, List<String>> specialSpec = null;

        if (spuDetail != null){
            // 获取通用规格参数
            genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
            //获取特有规格参数  使用自定义工具类解析数据
            specialSpec = JsonUtils.nativeRead(spuDetail.getSpecialSpec(),
                    new TypeReference<Map<Long, List<String>>>() {});
        }

        //将参数填入map
        Map<String,Object> specs = new HashMap<>();

        if (params != null){
            for (SpecParam param : params) {
                // 规格名字 key
                String key = param.getName();
                Object value = "";

                //规格参数 value
                if(param.getGeneric()){
                    // 通用参数的数值类型有分段的情况存在，要做一个处理,不能按上面那种方法获得value
                    value = genericSpec.get(param.getId());
                    //判断是否为数值类型 处理成段,覆盖之前的value
                    if(param.getNumeric()){
                        value = chooseSegment(value.toString(),param);
                    }
                }else {
                    // 特殊属性
                    value = specialSpec.get(param.getId());
                }
                value = (value == null ? "其他" : value);
                specs.put(key,value);
            }
        }else {
            logger.warn("将参数填入map失败, 因为params数据查询为空, 跳过执行, googs数据组装并返回");
        }

        goods.setAll(all);   // 搜索字段，包含标题、分类、品牌、规格
        goods.setSkus(JsonUtils.serialize(skus)); // 所有sku的集合的json格式
        goods.setPrice(priceList);  // 所有sku的价格集合
        goods.setSpecs(specs);      // 所有可搜索的规格参数

        logger.info("goods数据查询完成, 返回goods数据 >>>>>>>>>>>>>>>>>>>>>>>> ");
        return goods;
    }

    /**
     * 处理数值区间
     */
    private String chooseSegment(String value, SpecParam p){
        double val = NumberUtils.toDouble(value);
        String result = "其它";

        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");

            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }

            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

}
