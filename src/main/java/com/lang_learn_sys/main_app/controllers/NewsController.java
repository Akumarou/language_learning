package com.lang_learn_sys.main_app.controllers;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NewsController {
    @RequestMapping("/news")
    public String getNews(){
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "12345");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "/news";
    }
}
