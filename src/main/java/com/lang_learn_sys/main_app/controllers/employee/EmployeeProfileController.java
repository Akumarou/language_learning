package com.lang_learn_sys.main_app.controllers.employee;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.employee.entity.Employee;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import com.lang_learn_sys.main_app.security.entity.User;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmployeeProfileController {
    @Autowired
    EmployeeService theEmployeeService;
    @Autowired
    UserService theUserService;
    @Autowired
    BCryptPasswordEncoder theBCryptPasswordEncoder;
    @GetMapping("employee/profile")
    public String getEmployee(Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        model.addAttribute("theEmployee",employeeById);
        return "employee/profile";
    }
    @PostMapping("employee/profile")
    public String changeEmplProfile(Model model){if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        model.addAttribute("theEmployee",employeeById);
        return "employee/changeProfile";
    }
    @PostMapping("employee/changeEmplInfo")
    public String saveEmplInfoProfile(@RequestParam(name = "email",required = true)String email,
                                  Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        employeeById.setEmail(email);
        theEmployeeService.addOrUpdateEmployee(employeeById,theUserService);
        model.addAttribute("theEmployee",employeeById );
        model.addAttribute("info","Успешно обновлен профиль!");
        return "employee/changeProfile";
    }
    @PostMapping("employee/changeEmplPassword")
    public String saveEmplPassProfile(@RequestParam(name = "oldPass",required = true)String oldPass,
                                  @RequestParam(name = "newPass",required = true)String newPass,
                                  @RequestParam(name = "repPass",required = true)String repPass,
                                  Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        if(!newPass.equals(repPass)){
            model.addAttribute("err","Пароли должны быть одинаковы!");
            model.addAttribute("theEmployee",employeeById);
            return "employee/changeProfile";
        }
        User userById = theUserService.findUserById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(!theBCryptPasswordEncoder.matches(oldPass,userById.getPassword())){
            model.addAttribute("theEmployee",employeeById);
            model.addAttribute("err","Старый пароль не правильный!");
            return "employee/changeProfile";
        }
        userById.setPassword(theBCryptPasswordEncoder.encode(newPass));
        theUserService.updateUser(userById);
        model.addAttribute("theEmployee",employeeById);
        model.addAttribute("infopass","Успешно изменен пароль!");
        return "employee/changeProfile";
    }
}
