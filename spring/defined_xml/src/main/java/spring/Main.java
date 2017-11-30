package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by boying on 17/4/12.
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext bf = new ClassPathXmlApplicationContext("spring.xml");
        User user = (User) bf.getBean("user0");
        System.out.println(user.getUserName() + ", " + user.getEmail());
    }

}
