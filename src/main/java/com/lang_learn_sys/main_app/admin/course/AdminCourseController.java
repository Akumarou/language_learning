package com.lang_learn_sys.main_app.admin.course;

import com.lang_learn_sys.main_app.course.entity.Course;
import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class AdminCourseController {

    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;



    /*
    главный контроллер как для всех курсов, так и для вложенных элементов
     */
    @GetMapping("admin/course/list")
    public String getAllCourses(
            @RequestParam(name = "action", required = false, defaultValue = "none") String givenAction,
            @RequestParam(name = "id", required = false, defaultValue = "0") String idS,
            Model model) {
        long id = Long.parseLong(idS);
        switch (givenAction) {
            case "update":
                model.addAttribute("allEmployees", theEmployeeService.getAllEmployeesByRole("ROLE_INSTRUCTOR"));
                model.addAttribute("theCourse", (id <= 0 || theCourseService.getCourseById(id) == null) ? new Course() : theCourseService.getCourseById(id));
                if(!(id <= 0 || theCourseService.getCourseById(id) == null)){
                    List<CourseTopic> topics = new ArrayList<>(theCourseService.getCourseById(id).getTopics());
                    List<CourseTopic> result = new ArrayList<>();
                    for (int i=0;i<100;i++)
                        for(CourseTopic temp:topics)
                            if(temp.getId()==i)
                                result.add(temp);
                    model.addAttribute("allTopics",result);
                }
                return "/admin/course/change";
            case "delete":
                if (id <= 0) return "redirect:/admin/course/err";
                return (theCourseService.deleteCourseById(id)) ? "redirect:/admin/course/all" : "redirect:/admin/course/err";
            default:
                return "redirect:/admin/course/all";
        }
    }

    /*
    Контроллер изменения курсов
     */
    @PostMapping("/admin/course/change")
    public String saveOrUpdateCourse(@ModelAttribute(name = "theCourse") Course theCourse) {
        return theCourseService.addOrUpdateCourse(theCourse) ? "redirect:/admin/course/all" : "redirect:/admin/course/err";
    }


}
