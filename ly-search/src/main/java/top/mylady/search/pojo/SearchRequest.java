package top.mylady.search.pojo;


public class SearchRequest {

    private String key;
    private Integer page;

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



}
