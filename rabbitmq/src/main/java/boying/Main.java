package boying;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by boying on 2017/10/1.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RabbitTemplate directTemplate = classPathXmlApplicationContext.getBean("directTemplate", RabbitTemplate.class);
        RabbitTemplate fanoutTemplate = classPathXmlApplicationContext.getBean("fanoutTemplate", RabbitTemplate.class);
        RabbitTemplate topicTemplate = classPathXmlApplicationContext.getBean("topicTemplate", RabbitTemplate.class);

        while (true){
            directTemplate.convertAndSend("binding_key_1", "send to direct, routing key is binding_key_1");
            directTemplate.convertAndSend("binding_key_2", "send to direct, routing key is binding_key_2");
            directTemplate.convertAndSend("binding_key_3", "send to direct, routing key is binding_key_3");

            fanoutTemplate.convertAndSend("send to fanout");

            topicTemplate.convertAndSend("a.x", "send to topic, routing key is a.x");
            topicTemplate.convertAndSend("x.b", "send to topic, routing key is x.b");
            topicTemplate.convertAndSend("a.b", "send to topic, routing key is a.b");

            Thread.sleep(1000);
        }

    }
}
