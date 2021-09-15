package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Category;
import java.util.List;


public interface Goods_CategoryMapper {

    List<Category> selectByParentId(@Param("pid") Long pid);

    /**
     * 商品分类查询
     */
    List<Category> queryByIds(@Param("ids") List<Long> ids);

    //根据cid1, cid2, cid3 查询商品分类
    List<Category> select(@Param("cids") List<Long> ids);

}
