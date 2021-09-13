package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Brand;
import java.util.List;


/**
 * 品牌查询
 */
public interface BrandMapper {


    List<Brand> queryAll();

    /**
     * 品牌查询
     */
    Brand queryById(@Param("id")Long id);

}
