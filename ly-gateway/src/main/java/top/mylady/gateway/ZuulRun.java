package top.mylady.gateway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
@EnableDiscoveryClient  //开启Eureka客户端发现
@EnableZuulProxy
public class ZuulRun {

    public static void main(String[] args) {

        SpringApplication.run(ZuulRun.class, args);
    }
}
