package com.fiveware.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by valdisnei on 24/03/17.
 */
@Component
@Aspect
public class AspectRestApi {

    public static final String X_API_KEY = "x-api-key";
    public static final String X_FORWARDED_FOR = "X-FORWARDED-FOR";
    static Logger logger = LoggerFactory.getLogger(AspectRestApi.class);

    @AfterReturning(pointcut = "execution(public * com.fiveware.controller.AgentController.*(..))", returning = "returnValue")
    public void interceptarApiOy(JoinPoint joinPoint, ResponseEntity returnValue) throws JsonProcessingException {

        Object bot = joinPoint.getArgs()[0];

        Object requestPayLoad = joinPoint.getArgs()[2];

        HttpServletRequest context = (HttpServletRequest) joinPoint.getArgs()[3];

        String json = "{ Request:{}, " +
                " url:{}, " +
                " IP:{}, " +
                " Status:{}, " +
                " Response:{}, " +
                " Servico:{}, " +
                X_API_KEY + ":{}" + "}";

        logger.info(json,requestPayLoad,context.getRequestURI(),context.getHeader(X_FORWARDED_FOR),
                returnValue.getStatusCode(),
                returnValue.getBody(),bot,context.getHeader(X_API_KEY));

    }
}

