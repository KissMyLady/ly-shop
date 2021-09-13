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
import top.mylady.item.mappers.BrandMapper;
import top.mylady.item.mappers.Goods_CategoryMapper;
import top.mylady.item.mappers.SpuMapper;
import top.mylady.item.pojo.Brand;
import top.mylady.item.pojo.Category;
import top.mylady.item.pojo.Spu;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
}
