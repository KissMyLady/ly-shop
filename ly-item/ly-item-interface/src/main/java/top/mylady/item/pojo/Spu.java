package top.mylady.item.pojo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
public class Spu {

    private Long id;
    private Long brandId;
    private Long cid1;// 1级类目
    private Long cid2;// 2级类目
    private Long cid3;// 3级类目
    private String title;// 标题
    private String subTitle;// 子标题
    private Boolean saleable;// 是否上架

    @JsonIgnore
    private Boolean valid;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间

    @JsonIgnore  //返回时可以忽略这个字段
    private Date lastUpdateTime;// 最后修改时间


    //po是持久层数据，vo是new出来中间是程序使用的，隐私数据不能暴露
    private String cname;  //这两个字段不是数据库字段，但是放在这里通用mapper会把它当成数据库字段，所以要添加一个注解
    private String bname;

    private List<Sku> skus;

    private SpuDetail spuDetail;
}
