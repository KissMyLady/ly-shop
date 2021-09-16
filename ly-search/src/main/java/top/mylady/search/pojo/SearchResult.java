package top.mylady.search.pojo;
import top.mylady.common.vo.PageResult;
import top.mylady.item.pojo.Brand;

import java.util.List;
import java.util.Map;


/**
 * 扩展返回的结果
 */
public class SearchResult extends PageResult<Goods> {

    //扩展分类集合
    private List<Map<String, Object>> categories;

    //扩展品牌集合
    private List<Brand> brands;

    //新增参数 保存规格参数过虑条件
    private List<Map<String, Object>> specs;

    public void __init__(List<Goods> items,
                         Long total,
                         Integer totalPage,
                         List<Map<String, Object>> categories, List<Brand> brands,
                         List<Map<String, Object>> specs){
        super.total = total;
        super.totalPage = totalPage;
        super.items = items;

        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(){};

    //类重载
    public SearchResult(List<Map<String, Object>> categories,
                        List<Brand> brands,
                        List<Map<String, Object>> specs){
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    //类重载
    public SearchResult(List<Goods> items,
                        Long total,
                        List<Map<String, Object>> categories,
                        List<Brand> brands,
                        List<Map<String, Object>> specs){
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    //类重载
    public SearchResult(List<Goods> items,
                        Long total,
                        Integer totalPage,
                        List<Map<String, Object>> categories, List<Brand> brands,
                        List<Map<String, Object>> specs){
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    /**
     * 以下是set与get方法
     */
    public List<Map<String, Object>> getCategories(){
        return this.categories;
    }

    public void setCategories(List<Map<String, Object>> categories){
        this.categories = categories;
    }

    public List<Brand> getBrands(){
        return this.brands;
    }

    public void setBrands(List<Brand> brands){
        this.brands = brands;
    }

    public void setSpecs(List<Map<String, Object>> specs){
        this.specs = specs;
    }

    public List<Map<String, Object>> getSpecs(){
        return this.specs;
    }
}
