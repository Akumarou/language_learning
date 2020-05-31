package com.lang_learn_sys.main_app.controllers.instructor;

import com.lang_learn_sys.main_app.course.entity.Course;
import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.entity.Employee;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import com.lang_learn_sys.main_app.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InstructorCourseController {
    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;

    @GetMapping("instructor/listCourses")
    public String getAllCoursesForInstructor(Model model){
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        model.addAttribute("courses",theCourseService.getAllCoursesForTeacher(employeeById));
        return "instructor/listCourses";
    }
    @PostMapping("instructor/changeCourse")
    public String changeCourseInstructor(
            @RequestParam(name = "id",required = true)String course_id,
            Model model){
        Long id = Long.parseLong(course_id);
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Employee employeeById = theEmployeeService.getEmployeeById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if(employeeById==null)return "index";
        model.addAttribute("theCourse", theCourseService.getCourseById(id));
        return "instructor/changeCourse";
    }
    @PostMapping("instructor/changeCourse/save")
    public String saveOrUpdateCourseInstructor( @RequestParam(name = "id",required = true)String course_id,
                                                @RequestParam(name = "shortinfo",required = true)String shortinfo,
                                                Model model) {
        Course courseById = theCourseService.getCourseById(Long.parseLong(course_id));
        courseById.setInfo(shortinfo);
        theCourseService.addOrUpdateCourse(courseById) ;
        return "instructor/changeCourse";
    }
}
