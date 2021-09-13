package top.mylady.item.pojo;
import lombok.Data;


@Data
public class Stock {

    private Long skuId;
    private Integer seckillStock;  // 秒杀可用库存
    private Integer seckillTotal;  // 已秒杀数量
    private Integer stock;         // 正常库存

}
