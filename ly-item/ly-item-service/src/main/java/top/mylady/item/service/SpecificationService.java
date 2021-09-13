package top.mylady.item.service;
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
    public ResponseEntity queryParams(Long gid){
        List<SpecParam> params = specMapper.queryParams(gid);
        if (CollectionUtils.isEmpty(params)){
            return new ResponseEntity<>("无数据", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(params);
    }

}
