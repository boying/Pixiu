package boying.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by boying on 2017/9/29.
 */
public class QueueToDirect1Listener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        try {
            String msgBody = new String(message.getBody(), "UTF-8");
            System.out.println("queue_to_direct 1 listener recv: " + msgBody);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
