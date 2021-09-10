package top.mylady.item.pojo;
import lombok.Data;
import java.util.List;


@Data
public class Category {

    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;

    private List<Category> categoryList;
}
