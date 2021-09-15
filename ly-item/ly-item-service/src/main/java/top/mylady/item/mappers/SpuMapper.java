package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Spu;
import top.mylady.item.pojo.SpuDetail;

import java.util.List;


public interface SpuMapper {

    List<Spu> querySpu(@Param("saleableFlag") Integer saleableFlag,
                                       @Param("key") String key);

    SpuDetail selectByPrimaryKey(@Param("spuId") Long spuId);
}
