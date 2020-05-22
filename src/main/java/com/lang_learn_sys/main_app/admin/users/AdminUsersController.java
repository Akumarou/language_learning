package com.lang_learn_sys.main_app.admin.users;


import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.service.RoleService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class AdminUsersController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService theRoleService;
    @Autowired
    CustomerService theCustomerService;

    @GetMapping("/admin/user/roles")
    public String changeRole(
            @RequestParam(name="userId",required = true) String idStr,
            Model model
    ) {
        Long id=Long.parseLong(idStr);
        Set<Role> execludedRoles = new HashSet<Role>(theRoleService.getAllRoles());
        Set<Role> includedRoles = new HashSet<Role>(userService.findUserById(id).getRoles());
        for (Role x : includedRoles)
            execludedRoles.remove(x);
        model.addAttribute("theUser", userService.findUserById(id));
        model.addAttribute("includedRoles", includedRoles);
        model.addAttribute("execludedRoles", execludedRoles);
        return "admin/user/roles";
    }
    @PostMapping("/admin/user/roles")
    public String setRole(
            @RequestParam(name="userId",required = true) String idStr,
            @RequestParam(name="selectedRoles",required = true)String[] newRoles,
            Model model) {
        Long id=Long.parseLong(idStr);
        userService.updateUserRoles(id,theRoleService.getAllByNames(newRoles));
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/user/list";
    }

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
