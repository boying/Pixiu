package boying;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by boying on 2017/9/30.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        StringRedisTemplate template = context.getBean(StringRedisTemplate.class);
        RedisUtils redisUtils = new RedisUtils(template);
    }
}
