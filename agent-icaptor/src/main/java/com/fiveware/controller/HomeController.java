package com.fiveware.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by valdisnei on 29/05/17.
 */

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}


