package boying;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;

/**
 * Created by boying on 2017/9/29.
 */
public class MyMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        try {
            System.out.printf("recv message:");
            System.out.printf(new String(message.getBody() ,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
