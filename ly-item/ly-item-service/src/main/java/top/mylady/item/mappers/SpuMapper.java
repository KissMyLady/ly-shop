package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Spu;
import java.util.List;


public interface SpuMapper {

    public abstract List<Spu> querySpu(@Param("saleableFlag") Integer saleableFlag,
                                       @Param("key") String key);

}
