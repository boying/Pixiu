package boying.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by boying on 2017/9/29.
 */
public class QueueToDirect2Listener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("####################QueueToDirect2Listener###############");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        if(true)
        throw new RuntimeException("xx");
        try {
            //Thread.sleep(1000000000000L);
        }catch (Throwable t){}

        if (false)
            throw new RuntimeException("hahaha");
        try {
            String msgBody = new String(message.getBody(), "UTF-8");
            System.out.println("queue_to_direct 2 listener recv: " + msgBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
