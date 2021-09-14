package top.mylady.item.service;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.common.utils.dtos.ResponseResult;
import top.mylady.item.mappers.Goods_CategoryMapper;
import top.mylady.item.mappers.User_UserMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import top.mylady.item.pojo.Category;
import top.mylady.item.pojo.User;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/**
 * 业务处理层
 */
@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    //@Autowired(required = false)  //注解不再去校验 userMapper 是否存在了。也就不会有警告了
    @Resource
    private User_UserMapper user_userMapper;

    @Resource
    private Goods_CategoryMapper goods_categoryMapper;

    /**
     * ids查询品牌分类服务
     */
    public ResponseEntity queryByIds(List<Long> ids){
        for (Long i : ids) {
            if (i < 0L){
                return new ResponseEntity<>("参数错误", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            List<Category> categoryStringList = goods_categoryMapper.queryByIds(ids);
            List<String> list = new ArrayList<>();
            for (Category category: categoryStringList){
                list.add(category.getName());
            }
            return ResponseEntity.ok(list);
        }
        catch (Exception e){
            logger.warn("警告, 查询错误, 原因是: "+ e);
            return new ResponseEntity<>(""+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * 查询商品分类, 返回相应
     */
    public ResponseEntity queryCategoryByPid(Long pid){
        if (pid == null || pid.longValue() < 0){
            pid = 1L;
        }
        try {
            List<Category> categoryList = goods_categoryMapper.selectByParentId(pid);
            return ResponseEntity.ok(categoryList);
        }
        catch (Exception e){
            logger.warn("警告, 查询错误, 原因是: "+ e);
            return new ResponseEntity<>(""+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<Category> queryCategoryListByPid(List<Long> ids) {
        //查询的对象需要自己new出来，并将这个对象中的非空字段作为查询条件
        logger.warn("queryCategoryListByPid 打印传递过来的ids: "+ ids);
        List<Category> list;
        try {
            list = goods_categoryMapper.select(ids);
        }
        catch (Exception e){
            System.out.println("错误, 原因e: "+ e);
            return null;
        }
        return list;
    }

    /**
     * 测试
     */
    public ResponseResult testUser(){
        User user = user_userMapper.queryUserById(43L);
        return ResponseResult.okResult(user);
    }

}
