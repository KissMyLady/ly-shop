package top.mylady.item.message;
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
public class SendMessageTest {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageTest.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void sendMessage(){
        logger.info("开始发送消息");
        this.amqpTemplate.convertAndSend("item.Hello RabbitMQ");
        logger.info("消息发送成功");
    }

}
