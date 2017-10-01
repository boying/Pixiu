package boying;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by boying on 2017/10/1.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        RabbitTemplate rabbitTemplate = classPathXmlApplicationContext.getBean("rabbitTemplate", RabbitTemplate.class);

        Thread.sleep(5000);
        rabbitTemplate.convertAndSend("hello", "this is from idea");
    }
}
