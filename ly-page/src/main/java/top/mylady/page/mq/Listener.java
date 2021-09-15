package top.mylady.page.mq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class Listener {

    private static final Logger logger = LoggerFactory.getLogger(Listener.class);

    @RabbitListener(bindings=@QueueBinding(
            value=@Queue(name="spring.test.queue", durable = "true"),
            exchange=@Exchange(
                    name="spring.test.queue",
                    type=ExchangeTypes.TOPIC),
            key={"#.#"}
    ))
    public void listener(String msg){
        System.out.println("消息送达时间戳, 单位(毫秒): "+ System.currentTimeMillis());
        logger.warn("接受到的消息是: "+ msg);
    }

}
