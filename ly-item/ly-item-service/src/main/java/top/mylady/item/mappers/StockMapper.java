package top.mylady.item.mappers;
import org.apache.ibatis.annotations.Param;
import top.mylady.item.pojo.Stock;
import java.util.List;



/**
 * 库存查询
 */
public interface StockMapper {

    List<Stock> selectByIdList(@Param("ids")List<Long> ids);

}
