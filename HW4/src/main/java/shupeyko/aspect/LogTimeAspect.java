package shupeyko.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogTimeAspect {
    @Pointcut("@within(Timer)")
    public void annotatedClassPointcut() {
    }

    @Pointcut("@annotation(Timer)")
    public void annotatedMethodPointcut() {
    }

    @Pointcut("annotatedClassPointcut() || annotatedMethodPointcut()")
    public void targetPointcut() {
    }

    @Around("targetPointcut()")
    public Object measureSpeedOfMethod(ProceedingJoinPoint pjp) throws Throwable {
        Long start = System.nanoTime();
        Object object = pjp.proceed();
        Long executionTime = System.nanoTime() - start;
        log.info("Время выполнения метода  {} в классе {} составило: {} nanoseconds", pjp.getSignature().getName(), pjp.getTarget().getClass().getName(), executionTime);
        return object;
    }
}
