package shupeyko.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Before("execution(* shupeyko.services.*.*(..))")
    public void logInDebugMode(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Object[] args = jp.getArgs();
        log.error("Метод  {} с аргументами: {} начал выполнение", methodSignature, args);
    }
}
