package top.mylady.page.mq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(ListenerTest.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 消息发送的测试类
     * 延迟测试:
     *      消息发送: 1631679394393
     *      消息送达: 1631679394449
     *      延迟:     58毫秒(mm)
     */
    @Test
    public void sendMessage() throws Exception{
        String msg = "Hello Message Hello RabbitMQ 222223333 !";
        this.amqpTemplate.convertAndSend("spring.test.queue", "a.b", msg);

        System.out.println("消息发送当前的时间戳, 单位(毫秒): "+ System.currentTimeMillis());
        logger.info("消息发送成功, 沉睡10s后接收消息");
        Thread.sleep(10* 1000);

        logger.warn("测试线程结束 >>> ");
    }

}
