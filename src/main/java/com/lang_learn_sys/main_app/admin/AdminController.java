package com.lang_learn_sys.main_app.admin;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    CustomerService theCustomerService;
    @GetMapping("/admin")
    public String userList() { return "admin/index";}
}
