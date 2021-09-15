package top.mylady.item.service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.mylady.common.vo.PageResult;
import top.mylady.item.mappers.*;
import top.mylady.item.pojo.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    private CategoryService categoryService;

    @Resource
    private Goods_CategoryMapper goods_categoryMapper;

    @Resource
    private SpuMapper spuMapper;

    @Resource
    private BrandService brandService;

    @Resource
    private BrandMapper brandMapper;

    /**
     * 保存前端提交的form表单
     */
    public String saveGoods(Spu spu){

        spu.getSkus().forEach((sku)->{
            //新增sku

            //新增库存

            System.out.println("仅提供保存思路, 新增sku与库存模拟完成");
        });

        System.out.println("保存前端提交的form表单完成");
        return "ok";
    }


    /**
     * spu商品查询
     */
    public ResponseEntity querySpuByPage( Integer page,     Integer rows,
                                          Boolean saleable, String key){
        if (page * rows < -1){
            page = 1;
            rows = 5;
        }

        //判断key
        if (StringUtils.isBlank(key)){
            key = null;
        }

        Integer saleableFlag = 0;
        if(saleable == null){
            saleableFlag = 1;
        }else if (saleable == false){
            saleableFlag = 0;
        }else{
            saleableFlag = 1;
        }

        List<Spu> spus = null;
        //默认排序 last_update_time DESC
        //分页查询
        PageHelper.startPage(page, rows);
        try {
            spus = spuMapper.querySpu(saleableFlag, key);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
            if (CollectionUtils.isEmpty(spus)){
                return new ResponseEntity("未找到数据: "+e, HttpStatus.NOT_FOUND);
            }
        }
        if (CollectionUtils.isEmpty(spus)){
            return new ResponseEntity("未找到数据", HttpStatus.NOT_FOUND);
        }
        PageInfo<Spu> pageInfo = new PageInfo<Spu>(spus);

        //添加分类名称 手机/手机通讯/手机
        List<Spu> spuPlus = new ArrayList<>();
        for (Spu spu : spus) {

            //1);查询分类名称
            List<String> strCategory = queryCategory(
                    Arrays.asList(spu.getCid1(), spu.getCid2(),spu.getCid3())
            );
            spu.setCname(StringUtils.join(strCategory, "/"));

            //2);查询品牌信息
            String bName;
            try {
                Brand brand = brandMapper.queryById(spu.getBrandId());
                bName = brand.getName();
            }
            catch (Exception e){
                logger.warn("Goods => 查询品牌信息错误, 原因e: "+ e);
                bName = "空";
            }
            spu.setBname(bName);
            spuPlus.add(spu);
        }


        // System.out.println("总条数: "+ pageInfo.getTotal());
        // System.out.println("总页数："+ pageInfo.getPages());
        // 总条数："+pageInfo.getTotal());
        // 总页数："+pageInfo.getPages());
        // 当前页："+pageInfo.getPageNum());
        // 每页显示长度："+pageInfo.getPageSize());
        // 是否第一页："+pageInfo.isIsFirstPage());
        // 是否最后一页："+pageInfo.isIsLastPage());

        return ResponseEntity.ok(new PageResult<>(
                pageInfo.getTotal(),
                pageInfo.getPages(),
                spuPlus));
    }

    public List<String> queryCategory(List<Long> arr){
        List<Category> categoryStringList = goods_categoryMapper.queryByIds(arr);
        List<String> list = new ArrayList<>();
        for (Category category: categoryStringList){
            list.add(category.getName());
        }
        return list;
    }

    @Resource
    private SkuMapper skuMapper;

    @Resource
    private StockMapper stockMapper;

    public List<Sku> querySkuBySpuId(Long spuId) {

        List<Sku> skuList;
        /*
         * 获取的这个字段未转json
         * "ownSpec": "{\"4\":\"粉\",\"12\":\"4GB\",\"13\":\"32GB\"}",
         */
        //查询sku
        try {
            skuList = skuMapper.select(spuId);
        }
        catch (Exception e){
            logger.warn("查询sku列表错误, 原因e, 这是最核心的查询数据都没有查询到, 所以直接return空数据: "+ e);
            return null;
        }

        //查询库存
        //List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Long> ids = new ArrayList<>();
        for (Sku sku: skuList) {
            ids.add(sku.getId());
        }

        List<Stock> stockList = null;
        try {
           stockList = stockMapper.selectByIdList(ids);
        }
        catch (Exception e){
            logger.warn("库缓存查询错误, 原因e, 不是核心数据, 当前数据集为空, 不return"+ e);
        }

        //把stock变成一个map，其key：skuId,值：库存值
        if (stockList != null){
            Map<Long, Integer> stockMap = new HashMap<>();

//            stockMap = stockList.stream().collect(
//                    Collectors.toMap(
//                            Stock::getSkuId,
//                            Stock::getStock
//                    )
//            );
//            skuList.forEach(s ->s.setStock(stockMap.get(s.getId())));

            stockList.forEach((stock)->{
                stockMap.put(stock.getSkuId(), stock.getStock());
            });

            skuList.forEach((sku)->{
                Long skuId = sku.getId();               //获取到sku的id
                Integer skuStock = stockMap.get(skuId); //获取到库存
                sku.setStock(skuStock);                 //将库存赋值给对象
            });

        }else {
            logger.warn("警告 skuList 添加库存失败. 不是核心数据, 当前数据集为空, 不return");
        }

        logger.info("查询skuList数据完毕, 返回处理后的skuList数据");
        return skuList;
    }

    /**
     * 查询 tb_spu_detail 的详情信息
     */
    public SpuDetail queryDetailById(Long spuId) {
        try {
            SpuDetail spuDetail = spuMapper.selectByPrimaryKey(spuId);
            return spuDetail;
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
            return null;
        }
    }
}
