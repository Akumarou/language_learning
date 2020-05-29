package com.lang_learn_sys.main_app.admin.course;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.questions.entity.Answer;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionMultiple;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionSingle;
import com.lang_learn_sys.main_app.course.service.CourseService;
import com.lang_learn_sys.main_app.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminQuestionController {

    @Autowired
    CourseService theCourseService;
    @Autowired
    EmployeeService theEmployeeService;

    /*
    главный контроллер как для всех курсов, так и для вложенных элементов
     */
    @GetMapping("admin/course/changeQuestion")
    public String getAllQuestions(
            @RequestParam(name = "questionType", required = true) String qType,
            @RequestParam(name = "action", required = true) String givenAction,
            @RequestParam(name = "id", required = true) String idS,
            Model model) {
        long id = Long.parseLong(idS);
        model.addAttribute("questionType", qType);
        model.addAttribute("givenAction", givenAction);
        model.addAttribute("theTopicId", givenAction.equals("add") ? id : 0);
        switch (givenAction) {
            case "add":
                if (qType.equals("input"))
                    model.addAttribute("theQuestion", new CourseQuestionInput());
                else {
                    model.addAttribute("theQuestion", qType.equals("single") ?
                            new CourseQuestionSingle() : new CourseQuestionMultiple());
                    for (int i = 1; i <= 4; i++) {
                        model.addAttribute("question" + i, "");
                        model.addAttribute("checked" + i, false);
                    }
                }
                return "/admin/course/changeQuestion";
            case "update":
                if (id <= 0 || !theCourseService.questionExists(id, qType))
                    return "redirect:/admin/course/errQuestion";
                if (qType.equals("input"))
                    model.addAttribute("theQuestion", theCourseService.getInputQuestionById(id));
                else {
                    model.addAttribute("theQuestion", qType.equals("single") ? theCourseService.getSingleQuestionById(id) :
                            theCourseService.getMultipleQuestionById(id));
                    int i = 1;
                    if (qType.equals("single")) {
                        for (Answer ans : theCourseService.getSingleQuestionById(id).getAnswers()) {
                            if (ans.equals(theCourseService.getSingleQuestionById(id).getCorrectAnswer()))
                                model.addAttribute("correctAnsId", i);
                            model.addAttribute("question" + i, ans);
                            i++;
                        }
                    } else {
                        for (Answer ans : theCourseService.getMultipleQuestionById(id).getAnswers()) {
                            if (theCourseService.getMultipleQuestionById(id).getCorrectAnswers().contains(ans))
                                model.addAttribute("checked" + i, true);
                            else
                                model.addAttribute("checked" + i, false);
                            model.addAttribute("question" + i, ans);
                            i++;
                        }
                    }
                }
                return "/admin/course/changeQuestion";
            case "delete":
                if (id <= 0) return "redirect:/admin/course/errQuestion";
                return (theCourseService.deleteQuestionById(id, qType)) ? "redirect:/admin/course/all" : "redirect:/admin/course/errQuestion";
            default:
                return "redirect:/admin/course/all";
        }
    }

    /*
    Контроллеры изменения вопросов
     */
    @PostMapping("/admin/course/inputQuestion")
    public String saveOrUpdateInputQuestion(
            @RequestParam(name = "theTopicId", required = false, defaultValue = "_") String theTopicId,
            @RequestParam(name = "questionI", required = false, defaultValue = "_") String questionI,
            @RequestParam(name = "correctAnswerI", required = false, defaultValue = "_") String correctAnswerI,
            @RequestParam(name = "idI", required = false, defaultValue = "_") String idI) {
        if (!questionI.equals("_")) {
            CourseQuestionInput tempI = new CourseQuestionInput(questionI, correctAnswerI);
            if (!idI.equals("_")) tempI.setId(Long.parseLong(idI));
            tempI = theCourseService.addOrUpdateInputQuestion(tempI);
            if (idI.equals("_")) {
                CourseTopic topicById = theCourseService.getTopicById(Long.parseLong(theTopicId));
                topicById.addInputQ(tempI);
                theCourseService.justSave(topicById);
            }
        }
        return "redirect:/admin/course/all";
    }


    @PostMapping("/admin/course/singleQuestion")
    public String saveOrUpdateSingleQuestion(
            @RequestParam(name = "theTopicId", required = false, defaultValue = "_") String theTopicId,
            @RequestParam(name = "questionS", required = false, defaultValue = "_") String questionS,
            @RequestParam(name = "correctAnsS", required = false, defaultValue = "_") String correctAnsS,
            @RequestParam(name = "answerS1", required = false, defaultValue = "_") String answerS1,
            @RequestParam(name = "answerS2", required = false, defaultValue = "_") String answerS2,
            @RequestParam(name = "answerS3", required = false, defaultValue = "_") String answerS3,
            @RequestParam(name = "answerS4", required = false, defaultValue = "_") String answerS4,
            @RequestParam(name = "question1id", required = false, defaultValue = "_") String question1id,
            @RequestParam(name = "question2id", required = false, defaultValue = "_") String question2id,
            @RequestParam(name = "question3id", required = false, defaultValue = "_") String question3id,
            @RequestParam(name = "question4id", required = false, defaultValue = "_") String question4id,
            @RequestParam(name = "idS", required = false, defaultValue = "_") String idS) {
        int corrAns = Integer.parseInt(correctAnsS) - 1;
        Answer[] answers = new Answer[4];
        Answer temp = null;

        if (!question1id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question1id));
            if (!temp.getContent().equals(answerS1)) {
                temp.setContent(answerS1);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[0] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerS1);
            answers[0] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question2id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question2id));
            if (!temp.getContent().equals(answerS2)) {
                temp.setContent(answerS2);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[1] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerS2);
            answers[1] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question3id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question3id));
            if (!temp.getContent().equals(answerS3)) {
                temp.setContent(answerS3);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[2] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerS3);
            answers[2] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question4id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question4id));
            if (!temp.getContent().equals(answerS4)) {
                temp.setContent(answerS4);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[3] = temp;
        } else {temp = new Answer();temp.setContent(answerS4);
            answers[3] = theCourseService.addOrUpdateAnswer(temp);}

        CourseQuestionSingle cqtemp = new CourseQuestionSingle();
        cqtemp.setQuestion(questionS);
        if (!idS.equals("_")) cqtemp.setId(Long.parseLong(idS));
        cqtemp.setAnswers(new HashSet<Answer>(Arrays.asList(answers)));
        cqtemp.setCorrectAnswer(answers[corrAns]);
        cqtemp = theCourseService.addOrUpdateSingleQuestion(cqtemp);
        if (!theTopicId.equals("_")) {
            CourseTopic topicById = theCourseService.getTopicById(Long.parseLong(theTopicId));
            topicById.addSingleQ(cqtemp);
            theCourseService.justSave(topicById);
        }

        return "redirect:/admin/course/all";
    }

    @PostMapping("/admin/course/multiQuestion")
    public String saveOrUpdateMultiQuestion(
            @RequestParam(name = "theTopicId", required = false, defaultValue = "_") String theTopicId,
            @RequestParam(name = "questionM", required = false, defaultValue = "_") String questionM,
            @RequestParam(name = "correctAnsM", required = false, defaultValue = "_") String[] correctAnsM,
            @RequestParam(name = "answerM1", required = false, defaultValue = "_") String answerM1,
            @RequestParam(name = "answerM2", required = false, defaultValue = "_") String answerM2,
            @RequestParam(name = "answerM3", required = false, defaultValue = "_") String answerM3,
            @RequestParam(name = "answerM4", required = false, defaultValue = "_") String answerM4,
            @RequestParam(name = "question1id", required = false, defaultValue = "_") String question1id,
            @RequestParam(name = "question2id", required = false, defaultValue = "_") String question2id,
            @RequestParam(name = "question3id", required = false, defaultValue = "_") String question3id,
            @RequestParam(name = "question4id", required = false, defaultValue = "_") String question4id,
            @RequestParam(name = "idM", required = false, defaultValue = "_") String idM) {
        Answer[] answers = new Answer[4];
        Answer temp = null;
        if (!question1id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question1id));
            if (!temp.getContent().equals(answerM1)) {
                temp.setContent(answerM1);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[0] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerM1);
            answers[0] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question2id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question2id));
            if (!temp.getContent().equals(answerM2)) {
                temp.setContent(answerM2);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[1] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerM2);
            answers[1] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question3id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question3id));
            if (!temp.getContent().equals(answerM3)) {
                temp.setContent(answerM3);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[2] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerM3);
            answers[2] = theCourseService.addOrUpdateAnswer(temp);
        }

        if (!question4id.equals("_")) {
            temp = theCourseService.getAnswerById(Long.parseLong(question4id));
            if (!temp.getContent().equals(answerM4)) {
                temp.setContent(answerM4);
                temp = theCourseService.addOrUpdateAnswer(temp);
            }
            answers[3] = temp;
        } else {
            temp = new Answer();
            temp.setContent(answerM4);
            answers[3] = theCourseService.addOrUpdateAnswer(temp);
        }

        CourseQuestionMultiple cqtemp = new CourseQuestionMultiple();
        cqtemp.setQuestion(questionM);
        if (!idM.equals("_")) cqtemp.setId(Long.parseLong(idM));
        cqtemp.setAnswers(new HashSet<Answer>(Arrays.asList(answers)));
        Set<Answer> corrAnswers = new HashSet<>();
        for (String i : correctAnsM) {
            corrAnswers.add(answers[(Integer.parseInt(i) - 1)]);
        }
        cqtemp.setCorrectAnswers(corrAnswers);
        cqtemp = theCourseService.addOrUpdateMultipleQuestion(cqtemp);
        if (!theTopicId.equals("_")) {
            CourseTopic topicById = theCourseService.getTopicById(Long.parseLong(theTopicId));
            topicById.addMultipleQ(cqtemp);
            theCourseService.justSave(topicById);
        }
        return "redirect:/admin/course/all";
    }
}
