package top.mylady.page.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 封装的发送消息
 */
@Service
public class MessageSendTemp {

    private static final Logger logger = LoggerFactory.getLogger(MessageSendTemp.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    private void sendMessage(Long id, String type){
        try {
            this.amqpTemplate.convertAndSend("item."+ type, id);
        }
        catch (Exception e){
            logger.warn("MQ发送错误, 原因是: "+ e);
        }
    }

}
