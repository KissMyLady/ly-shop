package top.mylady.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import top.mylady.search.pojo.Product;


@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long>{

    String abc = "ProductDao 数据范围对象";
    String abd = "通过ProductDao进行文档增删改查操作";
}
