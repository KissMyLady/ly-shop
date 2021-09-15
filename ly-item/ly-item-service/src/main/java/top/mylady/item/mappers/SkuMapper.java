package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Sku;
import java.util.List;


public interface SkuMapper {

    //通过spu id, 查询sku列表
    List<Sku> select(@Param("spuId") Long spuId);
}
