package top.mylady.search.pojo;
import java.util.Map;


public class SearchRequest {

    private String key;
    private Integer page;

    //排序字段
    private String sortBy;

    //是否降序
    private Boolean descending;

    //过滤字段
    private Map<String, String> filter;

    private static final Integer DEFAULT_SIZE = 20;  //分页数

    private static final Integer DEFAULT_PAGE = 1;   //默认页

    public String getKey(){
        return this.key;
    }

    public void setKey(String key){
        this.key = key;
    }

    public Integer getPage(){
        if (this.page != null){
            return DEFAULT_PAGE;
        }
        //获取页面时做一些校验, 不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize(){
        return DEFAULT_SIZE;
    }

    public Map<String, String> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, String> filter) {
        this.filter = filter;
    }


}
