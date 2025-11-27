package com.studybuddy.Service.Logging;

import com.studybuddy.Exception.StudyBuddyException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class ServiceLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Around("execution(public * com.studybuddy.Service..*.*(..)) && " +
            "!execution(* com.studybuddy.Service.Extractor..*(..)) && " +
            "@within(org.springframework.stereotype.Service)")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String traceId = UUID.randomUUID().toString().substring(0, 8);
        
        MDC.put("traceId", traceId);
        MDC.put("service", className);
        MDC.put("method", methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object[] args = joinPoint.getArgs();
            logMethodEntry(className, methodName, traceId, args);
            
            Object result = joinPoint.proceed();
            
            long duration = System.currentTimeMillis() - startTime;
            logMethodSuccess(className, methodName, traceId, duration);
            
            return result;
            
        } catch (StudyBuddyException e) {
            long duration = System.currentTimeMillis() - startTime;
            logKnownException(className, methodName, traceId, duration, e);
            throw e;
            
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            logUnknownException(className, methodName, traceId, duration, e);
            throw e;
            
        } finally {
            MDC.clear();
        }
    }

    private void logMethodEntry(String className, String methodName, String traceId, Object[] args) {
        StringBuilder argsStr = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) argsStr.append(", ");
            argsStr.append(args[i] != null ? args[i].getClass().getSimpleName() : "null");
        }
        log.debug("[{}] Entering {}.{} with args: [{}]", traceId, className, methodName, argsStr);
    }

    private void logMethodSuccess(String className, String methodName, String traceId, long duration) {
        log.info("[{}] {}.{} completed successfully in {}ms", traceId, className, methodName, duration);
    }

    private void logKnownException(String className, String methodName, String traceId, long duration, StudyBuddyException e) {
        log.warn("[{}] {}.{} failed after {}ms with {}: {} (code: {})", 
                traceId, className, methodName, duration, 
                e.getClass().getSimpleName(), e.getMessage(), e.getErrorCode());
    }

    private void logUnknownException(String className, String methodName, String traceId, long duration, Exception e) {
        log.error("[{}] {}.{} failed after {}ms with unexpected exception: {}", 
                traceId, className, methodName, duration, e.getMessage(), e);
    }
}
