package top.mylady.item.pojo;
import lombok.Data;

import java.util.List;


@Data
public class SpecGroup {

    private Long id;

    private Long cid;

    private String name;

    private List<SpecParam> params; // 该组下的所有规格参数集合
}
