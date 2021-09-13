package top.mylady.item.pojo;
import lombok.Data;


@Data
public class SpecParam {

    private Long id;
    private Long cid;
    private Long groupId;
    private String name;

    private Boolean numeric;  //numeric是一个关键字
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;

}
