package top.mylady.item.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import top.mylady.item.mappers.BrandMapper;
import top.mylady.item.pojo.Brand;

import javax.annotation.Resource;


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
}
