package com.example.jobscheduler.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * @author Hex
 * @date 13/6/2022 上午10:11
 */
@Slf4j
@Aspect
@Component
public class AopConfig {

    @Pointcut("execution(* com.example.jobscheduler.task.base.BaseHandler.execute(..))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("{},开始执行", Thread.currentThread().getName());
        Object proceed = proceedingJoinPoint.proceed();
        stopWatch.stop();
        log.info("{},执行完成,耗时:{} ms", Thread.currentThread().getName(), stopWatch.getTotalTimeMillis());
        return proceed;
    }
}
