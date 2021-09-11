package top.mylady.gateway.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class LyCorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        //1, 添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1), 允许的域, 不要写*, 否则cookie就无法使用了
        config.addAllowedOrigin("*");

        //2), 是否转发cookie信息
        config.setAllowCredentials(true);
        //3), 请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");

        //4), 允许的请求头信息
        config.addAllowedHeader("*");

        //2, 添加映射路径, 拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3, 返回新的CorsFilter
        return new CorsFilter(configSource);
    }

}
