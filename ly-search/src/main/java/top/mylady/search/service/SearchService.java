package top.mylady.search.service;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import top.mylady.common.vo.PageResult;
import top.mylady.item.pojo.Category;
import top.mylady.search.pojo.Goods;
import top.mylady.search.pojo.SearchRequest;
import top.mylady.search.repository.GoodsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;


/**
 * ElasticSearch搜索功能实现
 */
@Service
public class SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchService.class);

    @Autowired
    private GoodsDao goodsDao;

    /**
     * 关键字搜索服务
     */
    public PageResult<Goods> search(SearchRequest reuqest){

        logger.info("检索服务层 关键字搜索服务开始 >>>>>>>>>> ");

        String key = reuqest.getKey();
        if (StringUtils.isBlank(key)){
            return null;
        }

        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        //1; 对key进行全文检索
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key).operator(Operator.AND));

        //2; 通过sourceFilter设置返回的结果字段, 我们只要id, skus, subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(
                new String[]{"id", "all", "skus", "subTitle"}, null
        ));

        //3; 分页
        int page = reuqest.getPage();
        int size = reuqest.getSize();
        queryBuilder.withPageable(PageRequest.of(page -1, size));

        //4; 查询 获取结果
        Page<Goods> pageInfo = this.goodsDao.search(queryBuilder.build());
        logger.info("打印查询的结果pageInfo: "+ pageInfo);

        //5; 封装结果并返回
        return new PageResult<>(
                pageInfo.getTotalElements(),
                pageInfo.getTotalPages(),
                pageInfo.getContent()
        );
    }

}
