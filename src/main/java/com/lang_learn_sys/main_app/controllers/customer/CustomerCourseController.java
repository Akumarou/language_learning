package com.lang_learn_sys.main_app.controllers.customer;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.entity.TestResult;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.customer.service.TestService;
import com.lang_learn_sys.main_app.customer.testing.Test;
import com.lang_learn_sys.main_app.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.*;

@Controller
public class CustomerCourseController {
    @Autowired
    CustomerService theCustomerService;

    @Autowired
    CourseService theCourseService;

    @Autowired
    TestService theTestService;

    @GetMapping("customer/listCourses")
    public String getAllCustomerCourses(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Customer currentCustomer = theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        if (currentCustomer == null)
            return "redirect:/login";
        model.addAttribute("haveCourses", currentCustomer.getBoughtCourses() != null);
        model.addAttribute("allCourses", currentCustomer.getBoughtCourses());
        return "customer/listCourses";
    }

    @GetMapping("customer/recomendations")
    public String getCustomerRecomendations(
            @RequestParam(name = "course_id", required = true) String course_id,
            Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        Set<TestResult> successes = theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()).getSuccesses();
        if (successes == null) {
            long min = 999;
            for (CourseTopic t : theCourseService.userGetAllTopics(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                    Long.parseLong(course_id)))
                if (min > t.getId()) min = t.getId();

            model.addAttribute("needsRetry", false);
            model.addAttribute("nextCourse", theCourseService.userGetTopic(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                    Long.parseLong(course_id), min));
            model.addAttribute("course_id", course_id);
            return "customer/recomendations";
        }
        List<CourseTopic> course = new ArrayList<>();
        boolean haveRecomendations = false;
        List<CourseTopic> nextCourse = new ArrayList<>(theCourseService.getCourseById(Long.parseLong(course_id)).getTopics());
        for (TestResult tr : successes) {
            if (tr.getCourse_id().toString().equals(course_id)) {
                if (tr.getResult() <= 20) {
                    course = new ArrayList<>();
                    course.add(theCourseService.userGetTopic(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                            tr.getCourse_id(), tr.getTopic_id()));
                    model.addAttribute("recCourses", course);
                    model.addAttribute("nextCourse", null);
                    model.addAttribute("needsRetry", true);
                    model.addAttribute("course_id", course_id);
                    return "customer/recomendations";
                }
                if (tr.getResult() <= 60) {

                    haveRecomendations = true;
                    course.add(theCourseService.userGetTopic(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                            tr.getCourse_id(), tr.getTopic_id()));
                }
                nextCourse.remove(theCourseService.getTopicById(tr.getTopic_id()));
            }
        }
        if (nextCourse.isEmpty())
            model.addAttribute("nextCourse", null);
        else {
            long min = 999L;
            for (CourseTopic aaa : nextCourse) if (aaa.getId() < min) min = aaa.getId();
            model.addAttribute("nextCourse", theCourseService.getTopicById(min));
        }

        model.addAttribute("needsRetry", false);
        model.addAttribute("haveRecomendations", haveRecomendations);
        model.addAttribute("recCourses", course);
        model.addAttribute("course_id", course_id);
        return "customer/recomendations";
    }

    @GetMapping("customer/allTopics")
    public String getCustomerAllTopics(
            @RequestParam(name = "course_id", required = true) String course_id,
            Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        List<CourseTopic> courseTopics = theCourseService.userGetAllTopics(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(), Long.parseLong(course_id));
        List<CourseTopic> result = new ArrayList<>();
        for (int i = 0; i < 100; i++)
            for (CourseTopic temp : courseTopics)
                if (temp.getId() == i)
                    result.add(temp);
        model.addAttribute("course_id", course_id);
        model.addAttribute("allTopics", result);
        return "customer/allTopics";
    }

    @GetMapping("customer/topic")
    public String getCustomerTopic(
            @RequestParam(name = "course_id", required = true) String course_id,
            @RequestParam(name = "topic_id", required = true) String topic_id,
            Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        model.addAttribute("theTopic",
                theCourseService.userGetTopic(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                        Long.parseLong(course_id), Long.parseLong(topic_id)));

        model.addAttribute("course_id", course_id);
        return "customer/topic";
    }

    @GetMapping("customer/test")
    public String getCustomerTest(
            @RequestParam(name = "course_id", required = true) String course_id,
            @RequestParam(name = "topic_id", required = true) String topic_id,
            Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"))
            return "redirect:/login";
        if (theCourseService.userGetTopic(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId(),
                Long.parseLong(course_id), Long.parseLong(topic_id)) == null)
            return "redirect:/login";
        Test tempTest = new Test();
        tempTest.setCourse_id(Long.parseLong(course_id));
        tempTest.setTopic_id(Long.parseLong(topic_id));
        long max = 10;
        Random rnd = new Random();
        long temp = Math.abs(rnd.nextLong()) % 5 + 1;
        max -= temp;
        tempTest.setqSingle(theCourseService.userGetSingleQuestions(Long.parseLong(topic_id), temp));
        if (max < 5)
            temp = Math.abs(rnd.nextLong()) % max + 1;
        else
            temp = Math.abs(rnd.nextLong()) % 5 + 1;
        max -= temp;
        tempTest.setqMultiple(theCourseService.userGetMultipleQuestions(Long.parseLong(topic_id), temp));
        tempTest.setqInput(theCourseService.userGetInputQuestions(Long.parseLong(topic_id), max));
        model.addAttribute("theTest", tempTest);
        return "customer/test";
    }

    @PostMapping("customer/test")
    public String compleeteTest(
            @ModelAttribute(name = "theTest") Test tempTest,
            @RequestParam(name = "singleQuestionIds[]", required = true) String[] singleQuestionIds,
            @RequestParam(name = "multipleQuestionIds[]", required = true) String[] multipleQuestionIds,
            @RequestParam(name = "inputQuestionIds[]", required = true) String[] inputQuestionIds,
            @RequestParam(name = "inputAnswer[]", required = true) String[] inputAnswers,
            @RequestParam(name = "singleAnswerIds0", required = false, defaultValue = "_") String singleAnswerIds0,
            @RequestParam(name = "singleAnswerIds1", required = false, defaultValue = "_") String singleAnswerIds1,
            @RequestParam(name = "singleAnswerIds2", required = false, defaultValue = "_") String singleAnswerIds2,
            @RequestParam(name = "singleAnswerIds3", required = false, defaultValue = "_") String singleAnswerIds3,
            @RequestParam(name = "singleAnswerIds4", required = false, defaultValue = "_") String singleAnswerIds4,
            @RequestParam(name = "multipleAnswerIds0[]", required = false) String[] multipleAnswerIds0,
            @RequestParam(name = "multipleAnswerIds1[]", required = false) String[] multipleAnswerIds1,
            @RequestParam(name = "multipleAnswerIds2[]", required = false) String[] multipleAnswerIds2,
            @RequestParam(name = "multipleAnswerIds3[]", required = false) String[] multipleAnswerIds3,
            @RequestParam(name = "multipleAnswerIds4[]", required = false) String[] multipleAnswerIds4,
            Model model) {
        double countQuestions = 0;
        double totalResult = 0;
        if (singleQuestionIds.length >= 1) {
            countQuestions++;
            if (!singleAnswerIds0.equals("_"))
                totalResult += theCourseService.getSingleQuestionById(Long.parseLong(singleQuestionIds[0]))
                        .getCorrectivity(theCourseService.getAnswerById(Long.parseLong(singleAnswerIds0)));
        }
        if (singleQuestionIds.length >= 2) {
            countQuestions++;

            if (!singleAnswerIds1.equals("_"))
                totalResult += theCourseService.getSingleQuestionById(Long.parseLong(singleQuestionIds[1]))
                        .getCorrectivity(theCourseService.getAnswerById(Long.parseLong(singleAnswerIds1)));
        }
        if (singleQuestionIds.length >= 3) {
            countQuestions++;
            if (!singleAnswerIds2.equals("_"))
                totalResult += theCourseService.getSingleQuestionById(Long.parseLong(singleQuestionIds[2]))
                        .getCorrectivity(theCourseService.getAnswerById(Long.parseLong(singleAnswerIds2)));
        }
        if (singleQuestionIds.length >= 4) {
            countQuestions++;
            if (!singleAnswerIds3.equals("_"))
                totalResult += theCourseService.getSingleQuestionById(Long.parseLong(singleQuestionIds[3]))
                        .getCorrectivity(theCourseService.getAnswerById(Long.parseLong(singleAnswerIds3)));
        }
        if (singleQuestionIds.length >= 5) {
            countQuestions++;
            if (!singleAnswerIds4.equals("_"))
                totalResult += theCourseService.getSingleQuestionById(Long.parseLong(singleQuestionIds[4]))
                        .getCorrectivity(theCourseService.getAnswerById(Long.parseLong(singleAnswerIds4)));
        }
        for (int i = 0; i < inputQuestionIds.length; i++) {
            countQuestions++;
            totalResult += theCourseService.getInputQuestionById(Long.parseLong(inputQuestionIds[i]))
                    .getCorrectivity(inputAnswers[i]);
        }
        if (multipleQuestionIds.length >= 1) {
            countQuestions++;
            totalResult += theCourseService.getMultipleQuestionById(Long.parseLong(multipleQuestionIds[0]))
                    .getCorrectivity(theCourseService.getAnswerByIds(multipleAnswerIds0));
        }
        if (multipleQuestionIds.length >= 2) {
            countQuestions++;
            totalResult += theCourseService.getMultipleQuestionById(Long.parseLong(multipleQuestionIds[1]))
                    .getCorrectivity(theCourseService.getAnswerByIds(multipleAnswerIds1));
        }
        if (multipleQuestionIds.length >= 3) {
            countQuestions++;
            totalResult += theCourseService.getMultipleQuestionById(Long.parseLong(multipleQuestionIds[2]))
                    .getCorrectivity(theCourseService.getAnswerByIds(multipleAnswerIds2));
        }
        if (multipleQuestionIds.length >= 4) {
            countQuestions++;
            totalResult += theCourseService.getMultipleQuestionById(Long.parseLong(multipleQuestionIds[3]))
                    .getCorrectivity(theCourseService.getAnswerByIds(multipleAnswerIds3));
        }
        if (multipleQuestionIds.length >= 5) {
            countQuestions++;
            totalResult += theCourseService.getMultipleQuestionById(Long.parseLong(multipleQuestionIds[4]))
                    .getCorrectivity(theCourseService.getAnswerByIds(multipleAnswerIds4));
        }

        totalResult /= countQuestions;
        if (totalResult > 100D) totalResult = 100D;
        TestResult temp = new TestResult();
        temp.setCourse_id(tempTest.getCourse_id());
        temp.setTopic_id(tempTest.getTopic_id());
        for (TestResult t:theCustomerService.getCutomerById(((User) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal()).getId()).getSuccesses())
            if(temp.getCourse_id()==t.getCourse_id() && temp.getTopic_id()==t.getTopic_id())
                temp=t;
        temp.setResult((long) Math.round((float) totalResult));
        temp.setDate(new Date(System.currentTimeMillis()));
        theTestService.addOrUpdateTestResult(temp,
                ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        return "index";
    }
}
