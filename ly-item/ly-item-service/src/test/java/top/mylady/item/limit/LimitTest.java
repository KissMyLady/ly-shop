package top.mylady.item.limit;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LimitTest {

    private static final Logger logger = LoggerFactory.getLogger(LimitTest.class);

    /**
     * 每秒, 限制这个接口访问频率
     */
    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(1.0); // 这里的1表示每秒允许处理的量为1个

        for (int i = 1; i <= 10; i++) {
            limiter.acquire();  // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute:" +
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        long end = System.currentTimeMillis();
        System.out.println("time:" + (end - start) + "'ms");
    }

}
