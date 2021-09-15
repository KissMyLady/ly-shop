package top.mylady.item;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.mylady.item.mappers.BrandMapper;
import top.mylady.item.pojo.Brand;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class mybatis {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 分页助手参考: https://blog.csdn.net/weixin_42239949/article/details/102382852
     */
    @Test
    public void testMybatis(){

        PageHelper.startPage(1, 5);
        List<Brand> brandList = brandMapper.queryAll();

        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brandList);

        brandList.forEach((b)->{
            System.out.println("b: "+ b);
        });

        System.out.println("总条数："+pageInfo.getTotal());
        System.out.println("总页数："+pageInfo.getPages());
        System.out.println("当前页："+pageInfo.getPageNum());

        System.out.println("每页显示长度："+pageInfo.getPageSize());
        System.out.println("是否第一页："+pageInfo.isIsFirstPage());
        System.out.println("是否最后一页："+pageInfo.isIsLastPage());
    }
}
