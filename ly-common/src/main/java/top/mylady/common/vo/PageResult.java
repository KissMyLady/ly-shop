package top.mylady.common.vo;
import lombok.Data;
import java.util.List;


@Data
public class PageResult<T> {

    public Long total;        //总条数
    public Integer totalPage; //总页数
    public List<T> items;

    public PageResult() {

    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
