package com.company.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.company.controller.*.*(..))")
    private void forControllerPackage() {
    }

    @Pointcut("execution(* com.company.mapper.*.*(..))")
    private void forMapperPackage() {
    }

    @Pointcut("execution(* com.company.service.impl.*.*(..))")
    private void forServiceImplPackage() {
    }

    @Pointcut("forControllerPackage() || forMapperPackage() || forServiceImplPackage()")
    private void forAppFlow() {
    }

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {

        //display method we are calling
        String theMethod = theJoinPoint.getSignature().toShortString();
        log.info("====> in @Before: calling method: {}", theMethod);

        //get the arguments
        Object[] args = theJoinPoint.getArgs();

        //loop through the args and display them
        for (Object tempArg : args) {
            log.info("====> argument: {}", tempArg);
        }
    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

        //display method we are returning from
        String theMethod = theJoinPoint.getSignature().toShortString();
        log.info("====> in @AfterReturning: from method: {}", theMethod);

        //display data returned
        log.info("======> result: {}", theResult);
    }

    @AfterThrowing(
            pointcut = "forAppFlow()",
            throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {

        if (log.isDebugEnabled()) {
            //display thrown error if debug is enabled in properties file
            log.error("====> in @AfterThrowing: An error occurred in method {} with args {}",
                    joinPoint.getSignature().getName(), joinPoint.getArgs(), ex);
        }
    }
}