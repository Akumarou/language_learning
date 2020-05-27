package com.lang_learn_sys.main_app.admin.boughtCourses;

import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCustomerBoughtCoursesController {
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    CourseService theCourseService;

    @GetMapping("admin/bought/list")
    public String getAllCoursesForCustomers(Model model){
        model.addAttribute("allCustomers",theCustomerService.getAllCustomers());
        return "admin/bought/list";
    }
    @GetMapping("admin/bought/add")
    public String addCoursesForCustomer(
            @RequestParam(name = "userId", required = true)String userId,
            Model model){
        model.addAttribute("allCourses",theCourseService.getAllCourses());
        model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(userId)));
        return "admin/bought/add";
    }
    @PostMapping("admin/bought/add")
    public String saveNewCoursesForCustomer(
            @RequestParam(name = "userId", required = true)String userId,
            @RequestParam(name = "courseId", required = true)String courseId,
            Model model){
        theCourseService.addCourseToTheCustomer(Long.parseLong(userId),theCourseService.getCourseById(Long.parseLong(courseId)));
        model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(userId)));
        return "admin/bought/change";
    }
    @GetMapping("admin/bought/change")
    public String changeCoursesForCustomer(
            @RequestParam(name = "userId", required = true)String userId,
            Model model){
        model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(userId)));
        return "admin/bought/change";
    }

    @PostMapping("admin/bought/change")
    public String saveCoursesForCustomer(
            @RequestParam(name = "userId", required = true)String userId,
            @RequestParam(name = "courseId", required = true)String courseId,
            Model model){
        theCourseService.removeCourseOfTheCustomer(Long.parseLong(userId),theCourseService.getCourseById(Long.parseLong(courseId)));
        model.addAttribute("theCustomer",theCustomerService.getCutomerById(Long.parseLong(userId)));
        return "admin/bought/change";
    }

}
