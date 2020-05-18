package com.lang_learn_sys.main_app.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/home")
    public String goHome() {
        return "home";
    }

    @RequestMapping("/")
    public String basic() {
        return "home";
    }

    @RequestMapping("/hello")
    public String helloPage() {
        return "hello";
    }

    @RequestMapping("/login")
    public String logForm() {
        return "login";
    }

    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}