package top.mylady.search.service;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import top.mylady.item.pojo.Brand;
import top.mylady.item.pojo.Category;
import top.mylady.search.client.GoodsClient;
import top.mylady.search.pojo.Goods;
import top.mylady.search.pojo.SearchRequest;
import top.mylady.search.pojo.SearchResult;
import top.mylady.search.repository.GoodsDao;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Aggregations聚合查询; 这个类是SearchService 的升级版
 */
@Service
public class AggsSearchResult {

    private static final Logger logger = LoggerFactory.getLogger(AggsSearchResult.class);

    @Resource
    private GoodsDao goodsDao;

    @Autowired
    private GoodsClient goodsClient;

    /**
     * 升级版 集合搜索服务
     * TODO: 聚合搜索bug
     */
    public SearchResult search(SearchRequest request) {

        if (StringUtils.isBlank(request.getKey())){
            return null;
        }

        //1; 创建自定义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //添加查询条件
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND));

        //过滤结果集
        String[] allowFields = new String[]{"id", "subTitle", "skus"};  //允许字段
        String[] unAllowFields = new String[]{};                        //禁止字段
        FetchSourceFilter filter = new FetchSourceFilter(allowFields, unAllowFields);
        queryBuilder.withSourceFilter(filter);

        //获取分页参数
        Integer page = request.getPage();
        Integer size = request.getSize();

        //添加分页
        queryBuilder.withPageable((PageRequest.of(page -1, size)));

        String categoryAggName = "category";
        String brandAggName = "brands";

        //聚合分类
        TermsAggregationBuilder aggCate = AggregationBuilders.terms(categoryAggName).field("cid3");
        queryBuilder.addAggregation(aggCate);

        TermsAggregationBuilder aggBrand = AggregationBuilders.terms(brandAggName).field("brandId");
        queryBuilder.addAggregation(aggBrand);

        AggregatedPageImpl<Goods> goodsPageResult = (AggregatedPageImpl<Goods>) this.goodsDao.search(queryBuilder.build());
        //AggregatedPage<Goods> goodsPageResult = (AggregatedPage<Goods>) this.goodsDao.search(queryBuilder.build());

        //解析聚合结果
        logger.info("开始解析聚合查询数据 >>>>>>>>>> ");

        Aggregation cateAggs  = goodsPageResult.getAggregation(categoryAggName);
        Aggregation brandAggs = goodsPageResult.getAggregation(brandAggName);

        List<Map<String, Object>> categories = null;
        List<Brand> brands = null;

        try {
             brands = getBrandAggResult(brandAggs);
             categories = getCategoryAggResult(cateAggs);
        }
        catch (Exception e){
            logger.warn("解析错误, 原因e: "+ e);
        }

        //封装, 返回结果
        List<Goods> items = goodsPageResult.getContent();
        Long        total = goodsPageResult.getTotalElements();
        Integer totalPage = goodsPageResult.getTotalPages();

        logger.info("封装, 数据返回");

        return new SearchResult(
                items, total, totalPage,
                categories, brands);
    }

    //品牌解析
    private List<Brand> getBrandAggResult(Aggregation aggregation){
        logger.info("getBrandAggResult 品牌解析 >>> ");

        //处理集合结果集
        Terms terms;
        try {
            terms = (Terms) aggregation;
        }
        catch (Exception e){
            logger.warn("getBrandAggResult解析错误, 原因e: "+ e);
            return null;
        }

        //获取所有品牌id桶
        List<Brand> brands = new ArrayList<>();
        //List<?> buckets = terms.getBuckets();

        logger.info("品牌解析, for循环遍历bucket ");
        for (Terms.Bucket bucket: terms.getBuckets()) {
            Long brandId = bucket.getKeyAsNumber().longValue();
            //logger.info("查询到brandId, 打印: 开始查询品牌信息, 添加到列表返回"+ brandId);

            try {
                Brand brand = goodsClient.queryBrandById(brandId);
                //logger.info("打印解析到的brand: "+ brand);
                brands.add(brand);
            }
            catch (Exception e){
                logger.warn("错误, 原因e: "+ e);
            }
        }

        return brands;
    }

    /**
     * 分类解析
     */
    public List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation){
        //处理集合结果集
        Terms terms;
        try {
            terms = (Terms) aggregation;
        }
        catch (Exception e){
            logger.warn("getCategoryAggResult解析错误, 原因e: "+ e);
            return null;
        }

        //定义一个品牌集合, 搜索所有的品牌对象
        List<Long> cids = new ArrayList<>();

        //解析  获取所有的分类id, 遍历
        for (Terms.Bucket bucket: terms.getBuckets()) {
            Long cid = bucket.getKeyAsNumber().longValue();
            cids.add(cid);
        }

        List<String> names = this.queryCateNames(cids);
        List<Map<String, Object>> categories = new ArrayList<>();

        if (names == null){
            logger.warn("names查询为空, 返回null");
            return null;
        }
        logger.info("names查询不为空, 打印names: "+ names);

        for (int i = 0; i< cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id",   cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }

    /**
     * 方法调用, 查询品牌分类
     */
    private List<String> queryNamesByIds(List<Long> ids){
        logger.info("方法调用, 查询品牌分类 >> ");
        if (ids.isEmpty()){
            return null;
        }

        List<String> brandList = new ArrayList<>();
        List<Brand> brands = null;
        try {
            brands = this.goodsClient.queryBrandsByIds(ids);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
        }
        if (brands != null){
            logger.info("打印查询到的brands: "+ brands);
            for (Brand brand : brands) {
                brandList.add(brand.getName());
            }
        }else {
            logger.info("brands查询为空 ---- ");
        }

        return brandList;
    }

    /**
     * 方法调用, 查询分类
     */
    private List<String> queryCateNames(List<Long> cids){

        List<Category> categories = null;
        try {
            categories = goodsClient.queryCategoryByIds(cids);
        }
        catch (Exception e){
            logger.warn("错误, 原因e: "+ e);
        }

        if (categories.isEmpty()){
            return null;
        }

        List<String> categoryNames = new ArrayList<>();
        categories.forEach( (cate)->{
            categoryNames.add(cate.getName());
        });
        return categoryNames;
    }
}
