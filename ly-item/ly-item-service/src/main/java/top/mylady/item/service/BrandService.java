package top.mylady.item.service;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.item.mappers.BrandMapper;
import top.mylady.item.pojo.Brand;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BrandService {

    private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

    @Resource
    private BrandMapper brandMapper;

    /**
     * bid查询品牌信息
     */
    public Brand queryById(Long id){

        Brand brand = null;
        try {
            brand = brandMapper.queryById(id);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
            return null;
        }
        return brand;
    }

    public List<Brand> queryBrandsByIds(List<Long> ids){
        List<Brand> brands = brandMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(brands)){
            return null;
        }
        return brands;
    }

    public List<Brand> queryBrandsByCid(Long cid){
        List<Brand> brands;
        try {
            brands = this.brandMapper.queryByCid(cid);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
            return null;
        }
        return brands;
    }
}
