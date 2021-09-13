package top.mylady.item.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.pagehelper.PageInterceptor;
import java.util.Properties;


/**
 * 分页助手
 * 参考博客: https://www.cnblogs.com/jyfs/p/14393277.html
 */
@Configuration
public class PageHelperConfig {

    @Bean
    public PageInterceptor pageHelper(){
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();

        properties.setProperty("offsetAsPageNum", "true");     //将RowBounds第一个参数offset当成pageNum页码使用
        properties.setProperty("rowBoundsWithCount", "true");  //使用RowBounds分页会进行count查询
        properties.setProperty("reasonable", "true");          //如果pageNum<1或pageNum>pages会返回空数据
        properties.setProperty("returnPageInfo", "check");     //always总是返回PageInfo类型, check检查返回类型是否为PageInfo,none返回Page
        properties.setProperty("supportMethodsArguments", "true");  //支持通过Mapper接口参数来传递分页参数
        properties.setProperty("helperDialect", "mysql");      //配置数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;

    }
}
