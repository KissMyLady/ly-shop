package top.mylady.item.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.mylady.item.mappers.SpecMapper;
import top.mylady.item.pojo.SpecGroup;
import top.mylady.item.pojo.SpecParam;

import javax.annotation.Resource;
import java.util.List;


@Service
public class SpecificationService {

    private static final Logger logger = LoggerFactory.getLogger(SpecificationService.class);

    @Resource
    private SpecMapper specMapper;

    /**
     * 根据id查询分组
     */
    public ResponseEntity queryGroupsByUd(Long cid){

        List<SpecGroup> groups = specMapper.queryByCid(cid);

        if (CollectionUtils.isEmpty(groups)){
            return new ResponseEntity<>("无数据", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(groups);
    }

    /**
     * 规格参数查询
     */
    public ResponseEntity queryParams(Long gid, Long cid,   //cid: 商品分类id
                                      Boolean generic,      //是否为通用属性
                                      Boolean searching){   //是否搜索
        List<SpecParam> params;
        try {
            params = specMapper.queryParams(gid, cid, generic, searching);
        }
        catch (Exception e){
            logger.warn("规格参数查询错误, 原因e: "+ e);
            params = null;
        }
        if (CollectionUtils.isEmpty(params)){
            return new ResponseEntity<>("无数据", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(params);
    }

    /**
     * 查询指定规格参数 ok
     */
    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching) {

        Integer flag;
        if (searching == null){
            flag = null;
        }else if (searching == false){
            flag = 0;
        }else {
            flag = 1;
        }

        try {
            return specMapper.querySpecParams(gid, cid, flag);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
            return null;
        }
    }

}
