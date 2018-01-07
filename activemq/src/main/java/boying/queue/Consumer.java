package boying.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by boying on 2018/1/7.
 */
public class Consumer {
    // ConnectionFactory ：连接工厂，JMS 用它创建连接
    private static ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
            ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");

    public static void main(String[] args) throws JMSException {
        // Connection ：JMS 客户端到JMS Provider 的连接
        final Connection connection = connectionFactory.createConnection();
        connection.start();
        // Session： 一个发送或接收消息的线程
        final Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        // Destination ：消息的目的地;消息送谁那获取.
        Destination destination = session.createQueue("my-queue");
        // 消费者，消息接收者
        MessageConsumer consumer1 = session.createConsumer(destination);
        MessageConsumer consumer2 = session.createConsumer(destination);
        consumer1.setMessageListener(new MyMessageListener("consumer1", session));
        consumer2.setMessageListener(new MyMessageListener("consumer2", session));
    }

    public static class MyMessageListener implements MessageListener {
        private String name;
        private Session session;

        public MyMessageListener(String name, Session session) {
            this.name = name;
            this.session = session;
        }

        @Override
        public void onMessage(Message msg) {
            try {
                TextMessage message = (TextMessage) msg;
                System.out.println(name + "收到消息： " + message.getText());
                session.commit();
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
