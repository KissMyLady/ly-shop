package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.SpecGroup;
import top.mylady.item.pojo.SpecParam;

import java.util.List;


public interface SpecMapper {

    /**
     * 通过cid查询规格
     */
    List<SpecGroup> queryByCid(@Param("cid")Long cid);

    List<SpecParam> queryParams(@Param("gid")Long gid);

}

