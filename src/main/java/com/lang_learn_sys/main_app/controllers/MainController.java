package com.lang_learn_sys.main_app.controllers;


import com.lang_learn_sys.main_app.security.service.RoleService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class MainController implements WebMvcConfigurer {
    @Autowired
    UserService theUserService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/")
    public String showMain() {
        return "redirect:/index";
    }

    @RequestMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/index")
    public String showIndex(Model model) {
        String sht = "<div>\n" +
                "    <div sec:authorize=\"!isAuthenticated()\">\n" +
                "        <h4><a href=\"/login\">Войти</a></h4>\n" +
                "        <h4><a href=\"/registration\">Зарегистрироваться</a></h4>\n" +
                "    </div>\n" +
                "    <div sec:authorize=\"isAuthenticated()\">\n" +
                "        <h2 sec:authentication=\"name\"></h2>\n" +
                "        <h4><a href=\"/logout\">Выйти</a></h4>\n" +
                "    </div>\n" +
                "    <h4><a th:href=\"@{/news}\">Новости (только пользователь)</a></h4>\n" +
                "    <h4><a th:href=\"@{/admin}\">Пользователи (только админ)</a></h4>\n" +
                "</div>";
        model.addAttribute("content",sht);
        return "index";
    }
    @PostMapping("/index")
    public String showIndex(@RequestParam(name = "content")String content,
            Model model) {
        model.addAttribute("content",content);
        return "index";
    }

    @RequestMapping("/news")
    public String showNews() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("user", "12345");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "news";
    }
}