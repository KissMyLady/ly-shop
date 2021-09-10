package top.mylady.item.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.common.utils.dtos.ResponseResult;
import top.mylady.item.mappers.Goods_CategoryMapper;
import top.mylady.item.mappers.User_UserMapper;
import java.util.List;
import top.mylady.item.pojo.Category;
import top.mylady.item.pojo.User;

import javax.annotation.Resource;


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
     * 查询商品分类, 返回相应
     */
    public ResponseResult queryCategoryByPid(Long pid){
        if (pid == null || pid.longValue() < 0){
            return ResponseResult.errorResult(404, "参数不正确");
        }
        try {
            List<Category> categoryList = goods_categoryMapper.selectByParentId(pid);
            return ResponseResult.okResult(categoryList);
        }
        catch (Exception e){
            logger.warn("警告, 查询错误, 原因是: "+ e);
            return ResponseResult.okResult(404, ""+e);
        }
    }

    /**
     * 测试
     */
    public ResponseResult testUser(){
        User user = user_userMapper.queryUserById(43L);
        return ResponseResult.okResult(user);
    }

}
