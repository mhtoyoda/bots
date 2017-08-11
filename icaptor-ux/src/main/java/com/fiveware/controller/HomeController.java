package com.fiveware.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by valdisnei on 7/4/17.
 */
@Controller
public class HomeController {

    @GetMapping("/register")
    public String register(){
        return "pages/pages_register";
    }

    @GetMapping("/login")
    public String login(@AuthenticationPrincipal User user){
        if (user != null) {
            return "pages/index";
        }
        return "pages/pages_login";
    }
}
