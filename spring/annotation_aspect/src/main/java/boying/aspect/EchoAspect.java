package boying.aspect;

import boying.annotation.Echo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class EchoAspect {
    @Around("@annotation(boying.annotation.Echo)")
    public Object echo(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Echo annotation = method.getAnnotation(Echo.class);

        // 方法参数
        Object[] args = point.getArgs();

        System.out.println(annotation.before());

        try {
            Object proceed = point.proceed();
            System.out.println(annotation.after());
            return proceed;
        }catch (Throwable t){
            String[] exceptions = annotation.exceptions();
            System.out.println(exceptions);
            throw t;
        }
    }
}
