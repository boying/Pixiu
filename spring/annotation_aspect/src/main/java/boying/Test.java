package boying;

import boying.annotation.Echo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Test {
    @Echo(before = "BEFORE", after = "AFTER", exceptions = {"E1", "E2"})
    public void f1() {
        System.out.println("Test#f1 called");
    }

    @Echo(before = "BEFORE", after = "AFTER", exceptions = {"E1", "E2"})
    public void f2() {
        System.out.println("Test#f2 called");
        throw new RuntimeException("exception...");
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Test bean = context.getBean(Test.class);
        bean.f1();
        bean.f2();
    }
}
