package com.lang_learn_sys.main_app.controllers.instructor;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InstructorTopicController {

    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;

    @GetMapping("instructor/changeTopic")
    public String changeTopic(
            @RequestParam(name = "action", required = true) String givenAction,
            @RequestParam(name = "id", required = true) String idS,
            Model model) {
        long id = Long.parseLong(idS);
        if (givenAction.equals("add") || givenAction.equals("update")) {
            if (id <= 0)
                return "redirect:/instructor/listCourses";
            if (givenAction.equals("update"))
                if (theCourseService.getTopicById(id) == null)
                    return "redirect:/instructor/listCourses";
            model.addAttribute("theTopic", (givenAction.equals("add")) ? new CourseTopic() : theCourseService.getTopicById(id));
            model.addAttribute("theCourseId", (givenAction.equals("add")) ? id:0);
            return "instructor/changeTopic";
        } else if (givenAction.equals("delete")) {
            if (id <= 0) return "redirect:/instructor/listCourses";
            theCourseService.deleteTopicById(id) ;
            return   "redirect:/instructor/listCourses";
        }
        return "redirect:/instructor/listCourses";
    }
}
