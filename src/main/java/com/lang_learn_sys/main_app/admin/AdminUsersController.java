package com.lang_learn_sys.main_app.admin;


import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminUsersController {
    @Autowired
    UserService userService;
    @Autowired
    CustomerService theCustomerService;

    @GetMapping("/admin/user/list")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/user/list";
    }

    @PostMapping("/admin/user/list")
    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long userId,
                             @RequestParam(required = true, defaultValue = "") String action,
                             Model model) {
        if (action.equals("delete")) {
            userService.deleteUser(userId);
        }
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/user/list";
    }

//    @GetMapping("/admin/user/user/gt/{userId}")
//    public String gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "admin/user/user";
//    }
}
