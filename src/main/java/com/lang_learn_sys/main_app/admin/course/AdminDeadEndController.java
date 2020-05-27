package com.lang_learn_sys.main_app.admin.course;

import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminDeadEndController {

    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;

    /*
    Заглушка для отображения главной страницы
     */
    @RequestMapping("/admin/course/all")
    public String allCoursePage(Model model) {
        model.addAttribute("allCourses", theCourseService.getAllCourses());
        return "admin/course/list";
    }

    /*
    Заглушка для отображения ошибок
     */
    @RequestMapping("/admin/course/err")
    public String errorCoursePage(Model model) {
        model.addAttribute("hasErrors", true);
        model.addAttribute("error", "Неверно задан курс");
        model.addAttribute("allCourses", theCourseService.getAllCourses());
        return "/admin/course/list";
    }
    @RequestMapping("/admin/course/errTopic")
    public String errorTopicPage(Model model) {
        model.addAttribute("hasErrors", true);
        model.addAttribute("error", "Неверно задана тема");
        model.addAttribute("allCourses", theCourseService.getAllCourses());
        return "/admin/course/list";
    }
    @RequestMapping("/admin/course/errQuestion")
    public String errorQuestionPage(Model model) {
        model.addAttribute("hasErrors", true);
        model.addAttribute("error", "Неверно задан вопрос");
        model.addAttribute("allCourses", theCourseService.getAllCourses());
        return "/admin/course/list";
    }
}
