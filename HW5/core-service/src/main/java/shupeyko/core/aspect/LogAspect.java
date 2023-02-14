package shupeyko.core.aspect;

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
    // Написать аспект, который логирует в режиме debug вызовы всех медов с их аргументами

    @Before("execution(* shupeyko.core.services.*.*(..))")
    public void logInDebugMode(JoinPoint jp) {
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Object[] args = jp.getArgs();
        log.debug("Метод  {} с аргументами: {} начал выполнение", methodSignature, args);
    }
}
