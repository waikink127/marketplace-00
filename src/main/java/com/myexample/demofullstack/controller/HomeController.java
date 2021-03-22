package com.myexample.demofullstack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(){
        return "index.html";
    }

    @GetMapping("/android")
    public String anPage(){
        return "android.html";
    }

}
