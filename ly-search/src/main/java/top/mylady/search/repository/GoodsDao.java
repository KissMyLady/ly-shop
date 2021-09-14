package top.mylady.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.mylady.search.pojo.Goods;


@Repository
public interface GoodsDao extends ElasticsearchRepository<Goods, Long> {

    String abc = "Goods 数据范围对象";
}
