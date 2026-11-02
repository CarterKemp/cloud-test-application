package school.gcu.demo.logging;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import school.gcu.demo.repository.TaskRepository;


@Aspect
@Component
public class MethodLoggingAspect {

    @Around(
            "execution(public * school.gcu.demo.controller..*(..)) || "
                    + "execution(public * school.gcu.demo.service..*(..)) || "
                    + "(this(school.gcu.demo.repository.TaskRepository) && execution(public * *(..)))"
    )
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        
        Class<?> sourceClass = joinPoint.getThis() instanceof TaskRepository
                ? TaskRepository.class
                : signature.getDeclaringType();

        Logger logger = LoggerFactory.getLogger(sourceClass);
        String className = sourceClass.getSimpleName();
        String methodName = signature.getName();
        long startNanos = System.nanoTime();
        String result = "ERROR";

        logger.info("ENTER {}.{}", className, methodName);

        try {
            Object returnedValue = joinPoint.proceed();
            result = "SUCCESS";
            return returnedValue;
        } catch (Throwable exception) {
            // Passing the Throwable prints the complete stack trace in Logback.
            logger.error("EXCEPTION {}.{} type={}",
                    className, methodName, exception.getClass().getSimpleName(), exception);
            throw exception;
        } finally {
            long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos);
            // EXIT is logged on both successful and exceptional method paths.
            logger.info("EXIT {}.{} result={} durationMs={}",
                    className, methodName, result, durationMs);
        }
    }
}
