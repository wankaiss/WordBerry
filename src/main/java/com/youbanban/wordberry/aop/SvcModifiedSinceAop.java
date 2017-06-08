package com.youbanban.wordberry.aop;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SvcModifiedSinceAop {
    private Logger logger;

    public SvcModifiedSinceAop() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestMapping() {} //NOSONAR

    @Pointcut("execution(* com.youbanban.goldenkiwi.web.service..*.*(..))")
    public void methodPointcut() {} //NOSONAR

    @Around("requestMapping() && methodPointcut()")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = new StopWatch();
        String name = pjp.getSignature().getName();
        try {
            logger.debug("Invoking: " + name);
            sw.start();
            return pjp.proceed();
        } finally {
            sw.stop();
            if (sw.getTime() > 30000) {
            	logger.debug("pjp.getSignature().getName() "+name+"======== exec time: " + sw.getTime());
			}
            logger.debug("pjp.getSignature().getName() "+name+" Invoking: " + name + ", time: " + sw.getTime());
        }
    }
}
