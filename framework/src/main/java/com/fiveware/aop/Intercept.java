package com.fiveware.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by valdisnei on 23/05/17.
 */
@Component
@Aspect
public class Intercept {

    static Logger logger = LoggerFactory.getLogger(Intercept.class);


    @Before("execution(public * com.fiveware.Automation.*(..))")
    public void interceptarApiOy(JoinPoint joinPoint) throws JsonProcessingException {


        logger.info("teste aspect");
    }
}
