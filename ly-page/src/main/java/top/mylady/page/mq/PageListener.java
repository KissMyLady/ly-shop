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
public class PageListener {

    private static final Logger logger = LoggerFactory.getLogger(PageListener.class);

    /**
     * 创建页面监听
     */
    @RabbitListener(bindings=@QueueBinding(
            value=@Queue(name="mylady.create.web.queue", durable = "true"),
            exchange=@Exchange(
                    name="mylady.item.exchange",
                    type= ExchangeTypes.TOPIC),
            key={"item.insert"}
    ))
    public void listenerCreatePage(Long id){
        if (id == null){
            return;
        }
        logger.info("创建页面监听, 调用创建页面方法, 假设页面创建成功");
    }


    /**
     * 删除页面监听
     */
    @RabbitListener(bindings=@QueueBinding(
            value=@Queue(name="mylady.create.web.queue", durable = "true"),
            exchange=@Exchange(
                    name="mylady.item.exchange",
                    type= ExchangeTypes.TOPIC),
            key={"item.delete"}
    ))
    public void listenerDeletePage(Long id){
        if (id == null){
            return;
        }
        logger.info("删除页面操作, 调用删除方法, 假设页面删除成功");
    }
}
