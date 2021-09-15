package top.mylady.search.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


//数据实体类
@Data
@NoArgsConstructor   //注解在类上, 为类提供一个无参的构造方法
@AllArgsConstructor  //使用后添加一个构造函数, 该构造函数含有所有已声明字段属性参数
@ToString
@Document(indexName="goods", shards=3, replicas=1)
public class Goods {

    @Id
    private Long id;

    @Field(type=FieldType.Text)
    private String all;  //所有需要被搜索的信息

    @Field(type = FieldType.Keyword, index=false)
    private String subTitle;

    private Long brandId;  // 品牌id
    private Long cid1;     // 1级分类id
    private Long cid2;     // 2级分类id
    private Long cid3;     // 3级分类id

    private Date createTime;

    //// 价格，对应到elasticsearch/json中是数组，一个spu有多个sku，就有多个价格
    private Set<Long> price;  //SPU下, 全部sku信息

    @Field(type = FieldType.Keyword, index=false)
    private String skus;  //List<sku> sku子信息集合

    private Map<String, Object> specs;  //可搜索的规格参数, key是参数名, 值是参数值

}
