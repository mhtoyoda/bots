package com.fiveware.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by valdisnei on 7/4/17.
 */
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(){
        return "theme/index";
    }


}
