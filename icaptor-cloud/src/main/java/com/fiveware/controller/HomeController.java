package com.fiveware.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by valdisnei on 06/06/17.
 */
@RestController
public class HomeController {

    static Logger logger = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("/up")
    public String up(HttpServletRequest httpRequest){
        String remoteAddr = httpRequest.getRemoteAddr();

        logger.info("Agent UP: {}",remoteAddr);
        return "UP";
    }
    @GetMapping("/down")
    public String down(HttpServletRequest httpRequest){
        String remoteAddr = httpRequest.getRemoteAddr();

        logger.info("Agent DOWN: {}",remoteAddr);
        return "DOWN";
    }
}
