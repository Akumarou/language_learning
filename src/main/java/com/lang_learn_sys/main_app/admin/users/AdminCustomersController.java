package com.lang_learn_sys.main_app.admin.users;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCustomersController {
    @Autowired
    UserService userService;
    @Autowired
    CustomerService theCustomerService;

    @GetMapping("/admin/customer/list")
    public String showCustomers(
            @RequestParam(name = "action", required = false,defaultValue = "none")String givenAction,
            @RequestParam(name = "id", required = false,defaultValue = "0")String idCustStr,
            Model model){

        switch (givenAction) {
            case "add":
                model.addAttribute("theCustomer",new Customer());
                model.addAttribute("allUsers", userService.allUsers());
                return "/admin/customer/change";
            case "get":
                Long id = Long.parseLong(idCustStr);
                System.out.println("selected id: " + id);
                if(id<=0 || (theCustomerService.getCutomerById(id)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                    return "/admin/customer/list";
                }
                model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(idCustStr)));
                model.addAttribute("theUser", userService.findUserById(Long.parseLong(idCustStr)));
                return "/admin/customer/get";
            case "update":
                Long id_u = Long.parseLong(idCustStr);
                System.out.println("selected id: " + id_u);
                if(id_u<=0 || (theCustomerService.getCutomerById(id_u)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                    return "/admin/customer/list";
                }
                model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(idCustStr)));
                model.addAttribute("allUsers", userService.allUsers());
                return "/admin/customer/change";
            case "delete":
                Long id_d = Long.parseLong(idCustStr);
                System.out.println("selected id: " + id_d);
                if(id_d<=0 || !theCustomerService.deleteCustomerById(id_d)){
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                    return "/admin/customer/list";
                }
                model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                return "/admin/customer/list";
            default:
                model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
                return "/admin/customer/list";
        }
    }

    @PostMapping("/admin/customer/list")
    public String addOrUpdateCustomer(
            @ModelAttribute(name="theCustomer")Customer theCustomer, Model model){
        if(!theCustomerService.addOrUpdateCustomer(theCustomer,userService)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
        return "/admin/customer/list";
    }
}

