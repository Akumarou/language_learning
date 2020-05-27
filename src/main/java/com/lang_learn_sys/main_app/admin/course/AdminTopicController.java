package com.lang_learn_sys.main_app.admin.course;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminTopicController {

    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;


    /*
    Главный контроллер для тем
     */
    @GetMapping("admin/course/changeTopic")
    public String changeTopic(
            @RequestParam(name = "action", required = true) String givenAction,
            @RequestParam(name = "id", required = true) String idS,
            Model model) {
        long id = Long.parseLong(idS);
        if (givenAction.equals("add") || givenAction.equals("update")) {
            if (id <= 0)
                return "redirect:/admin/course/errTopic";
            if (givenAction.equals("update"))
                if (theCourseService.getTopicById(id) == null)
                    return "redirect:/admin/course/errTopic";
            model.addAttribute("theTopic", (givenAction.equals("add")) ? new CourseTopic() : theCourseService.getTopicById(id));
            model.addAttribute("allEmployees", theEmployeeService.getAllEmployeesByRole("ROLE_INSTRUCTOR"));
            model.addAttribute("theCourseId", (givenAction.equals("add")) ? id:0);
            return "admin/course/changeTopic";
        } else if (givenAction.equals("delete")) {
            if (id <= 0) return "redirect:/admin/course/errTopic";
            return (theCourseService.deleteTopicById(id)) ? "redirect:/admin/course/all" : "redirect:/admin/course/errTopic";
        }
        return "redirect:/admin/course/all";
    }

    /*
    Главный контроллер для сохранения тем
     */
    @PostMapping("/admin/course/changeTopic")
    public String saveTopic(@RequestParam(name = "tempCourseId",required = false,defaultValue = "0") String idS,
                            @ModelAttribute(name = "theTopic") CourseTopic theTopic) {
        if(!theCourseService.addOrUpdateTopic(theTopic))
            return "redirect:/admin/course/errTopic";
        if(Long.parseLong(idS)>0)
            if(!theCourseService.addTopicToCourse(theTopic,Long.parseLong(idS))){
                return "redirect:/admin/course/errTopic";
            }
        return "redirect:/admin/course/all";
    }
}
