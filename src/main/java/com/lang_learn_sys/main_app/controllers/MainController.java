package com.lang_learn_sys.main_app.controllers;


import com.lang_learn_sys.main_app.security.service.RoleService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class MainController implements WebMvcConfigurer {
    @Autowired
    UserService theUserService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/")
    public String showMain() {
        return "index";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping("/index")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/news")
    public String showNews() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "12345");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "news";
    }
}