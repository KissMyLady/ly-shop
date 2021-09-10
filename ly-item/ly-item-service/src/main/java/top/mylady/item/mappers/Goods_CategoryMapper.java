package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Category;
import java.util.List;


public interface Goods_CategoryMapper {

    List<Category> selectByParentId(@Param("pid") Long pid);

}
