package com.fiveware.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by valdisnei on 7/4/17.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "pages/pages_login";
    }


    @GetMapping("/register")
    public String register(){
        return "pages/pages_register";
    }


    @PostMapping("/login")
    public String login(){
        return "pages/index";
    }

}
