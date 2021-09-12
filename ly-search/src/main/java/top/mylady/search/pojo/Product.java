package top.mylady.search.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;


//数据实体类
@Data
@NoArgsConstructor   //注解在类上, 为类提供一个无参的构造方法
@AllArgsConstructor  //使用后添加一个构造函数, 该构造函数含有所有已声明字段属性参数
@ToString
@Document(indexName="product", shards=3, replicas=1)
public class Product {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Keyword, index = false)
    private String images;
}
