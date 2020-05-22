package com.lang_learn_sys.main_app.admin.users;

import com.lang_learn_sys.main_app.employee.entity.Employee;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminEmployeeController {
    @Autowired
    UserService userService;
    @Autowired
    EmployeeService theEmployeeService;

    @GetMapping("/admin/employee/list")
    public String showEmployees(
            @RequestParam(name = "action", required = false,defaultValue = "none")String givenAction,
            @RequestParam(name = "id", required = false,defaultValue = "0")String idEmplStr,
            Model model){

        switch (givenAction) {
            case "add":
                model.addAttribute("theEmployee",new Employee());
                model.addAttribute("allUsers", userService.allUsers());
                return "/admin/employee/change";
            case "get":
                Long id = Long.parseLong(idEmplStr);
                if(id<=0 || (theEmployeeService.getEmployeeById(id)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
                    return "/admin/employee/list";
                }
                model.addAttribute("theEmployee",theEmployeeService.getEmployeeById(id));
                model.addAttribute("theUser", userService.findUserById(id));
                return "/admin/employee/get";
            case "update":
                Long id_u = Long.parseLong(idEmplStr);
                if(id_u<=0 || (theEmployeeService.getEmployeeById(id_u)==null)) {
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
                    return "/admin/employee/list";
                }
                model.addAttribute("theEmployee",theEmployeeService.getEmployeeById(id_u));
                model.addAttribute("allUsers", userService.allUsers());
                return "/admin/employee/change";
            case "delete":
                Long id_d = Long.parseLong(idEmplStr);
                if(id_d<=0 || !theEmployeeService.deleteEmployeeById(id_d)){
                    model.addAttribute("hasErrors", true);
                    model.addAttribute("error", "Неверно задан пользователь");
                    model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
                    return "/admin/employee/list";
                }
                model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
                return "/admin/employee/list";
            default:
                model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
                return "/admin/employee/list";
        }
    }

    @PostMapping("/admin/employee/list")
    public String addOrUpdateEmployee(
            @ModelAttribute(name="theEmployee") Employee theEmployee, Model model){
        if(!theEmployeeService.addOrUpdateEmployee(theEmployee,userService)){
            model.addAttribute("hasErrors", true);
            model.addAttribute("error", "Возникла ошибка при обновлении (добавлении) :(");
        }
        model.addAttribute("allEmployees",theEmployeeService.getAllEmployees());
        return "/admin/employee/list";
    }
}
