package com.lang_learn_sys.main_app.controllers.customer;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
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
public class CustomerProfileController {
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    UserService theUserService;
    @Autowired
    BCryptPasswordEncoder theBCryptPasswordEncoder;


    @GetMapping("customer/profile")
    public String getProfile(Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        model.addAttribute("theClient",
                theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        return "customer/profile";
    }
    @PostMapping("customer/profile")
    public String changeProfile(Model model){if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        model.addAttribute("theClient",
                theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        return "customer/changeProfile";
    }
    @PostMapping("customer/changeCustomerInfo")
    public String saveInfoProfile(@RequestParam(name = "firstName",required = true)String firstName,
                                  @RequestParam(name = "lastName",required = true)String lastName,
                                  @RequestParam(name = "email",required = true)String email,
            Model model){if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        Customer cutomerById = theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        cutomerById.setFirstName(firstName);cutomerById.setLastName(lastName);cutomerById.setEmail(email);
        theCustomerService.addOrUpdateCustomer(cutomerById,theUserService);
        model.addAttribute("theClient",cutomerById );
        model.addAttribute("info","Успешно обновлен профиль!");
        return "customer/changeProfile";
    }
    @PostMapping("customer/changeCustomerPassword")
    public String savePassProfile(@RequestParam(name = "oldPass",required = true)String oldPass,
                                  @RequestParam(name = "newPass",required = true)String newPass,
                                  @RequestParam(name = "repPass",required = true)String repPass,
                                  Model model){if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
        return "redirect:/login";
        if(!newPass.equals(repPass)){
            model.addAttribute("err","Пароли должны быть одинаковы!");
            model.addAttribute("theClient",
                    theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
            return "customer/changeProfile";
        }
        User userById = theUserService.findUserById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(!theBCryptPasswordEncoder.matches(oldPass,userById.getPassword())){
            model.addAttribute("theClient",
                    theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
            model.addAttribute("err","Старый пароль не правильный!");
            return "customer/changeProfile";
        }
        userById.setPassword(theBCryptPasswordEncoder.encode(newPass));
        String username = userById.getUsername();
        theUserService.updateUser(userById);
        model.addAttribute("theClient",
                theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        model.addAttribute("infopass","Успешно изменен пароль!");
        return "customer/changeProfile";
    }
}
