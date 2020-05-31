package com.lang_learn_sys.main_app.controllers.customer;

import com.lang_learn_sys.main_app.course.entity.Course;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.security.entity.User;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class CustomerBuyController {
    @Autowired
    CustomerService theCustomerService;
    @Autowired
    CourseService theCourseService;
    @Autowired
    UserService theUserService;

    @GetMapping("customer/buy")
    public String showCoursesForCustomer(Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        List<Course> allCourses = theCourseService.getAllCourses();
        allCourses.removeAll(new ArrayList<>(theCustomerService.
                getCutomerById(((User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getId()).getBoughtCourses()));
        model.addAttribute("availableCourses",allCourses);
        return "customer/buy";
    }
    @PostMapping("customer/buy")
    public String buyCoursesForCustomer(
            @RequestParam(name = "id",required = true)String course_id,
            Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Course courseById = theCourseService.getCourseById(Long.parseLong(course_id));
        Customer cutomerById = theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        Set<Course> boughtCourses = cutomerById.getBoughtCourses();
        if(boughtCourses.contains(courseById))
            return "redirect:/login";
        boughtCourses.add(courseById);
        cutomerById.setBoughtCourses(boughtCourses);
        theCustomerService.addOrUpdateCustomer(cutomerById,theUserService);
        return "customer/buySuccess";
    }
}
