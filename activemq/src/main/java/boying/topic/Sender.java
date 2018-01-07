package boying.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by boying on 2018/1/7.
 */
public class Sender {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        //61616是ActiveMQ默认端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection =  connectionFactory.createConnection();
        connection.start();

        // Session： 一个发送或接收消息的线程
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        // Destination ：消息的目的地;消息发送给谁.
        Destination destination =  session.createTopic("my-topic");

        // MessageProducer：消息发送者
        MessageProducer producer =  session.createProducer(destination);

        // 设置不持久化，可以更改
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for(int i=0;i<100000;i++){
            String msg = "topic msg" +i;
            //创建文本消息
            TextMessage message = session.createTextMessage(msg);

            Thread.sleep(1000);
            //发送消息
            producer.send(message);
            session.commit();
            System.out.println(msg);
        }

        // session.commit();
        session.close();
        connection.close();
    }

}
