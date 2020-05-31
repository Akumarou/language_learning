package com.lang_learn_sys.main_app.course.service;

import com.lang_learn_sys.main_app.course.entity.Course;
import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import com.lang_learn_sys.main_app.course.questions.entity.Answer;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionMultiple;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionSingle;
import com.lang_learn_sys.main_app.course.questions.repo.AnswerRepository;
import com.lang_learn_sys.main_app.course.questions.repo.CQInputRepository;
import com.lang_learn_sys.main_app.course.questions.repo.CQMultipleRepository;
import com.lang_learn_sys.main_app.course.questions.repo.CQSingleRepository;
import com.lang_learn_sys.main_app.course.repo.CourseRepository;
import com.lang_learn_sys.main_app.course.repo.CourseTopicRepository;
import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.customer.service.CustomerService;
import com.lang_learn_sys.main_app.employee.entity.Employee;
import com.lang_learn_sys.main_app.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CourseService {
    @Autowired
    AnswerRepository theAnswerRepository;
    @Autowired
    CQInputRepository theCQInputRepository;
    @Autowired
    CQSingleRepository theCQSingleRepository;
    @Autowired
    CQMultipleRepository theCQMultipleRepository;
    @Autowired
    CourseTopicRepository theCourseTopicRepository;
    @Autowired
    CourseRepository theCourseRepository;
    @Autowired
    CustomerService theCustomerServce;
    @Autowired
    UserService theUserService;


    /*
    Функционал для админа по управлению подписками
     */
    @Transactional
    public List<Course> getPaidCourses(Long id) {
        Set<Course> temp = theCustomerServce.getCutomerById(id).getBoughtCourses();
        if (temp != null)
            return new ArrayList<Course>(temp);
        else
            return new ArrayList<Course>();
    }

    @Transactional
    public boolean addCourseToTheCustomer(Long id, Course course) {
        try {
            Customer cust = theCustomerServce.getCutomerById(id);
            cust.getBoughtCourses().add(course);
            theCustomerServce.addOrUpdateCustomer(cust, theUserService);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean removeCourseOfTheCustomer(Long id, Course course) {
        try {
            Customer cust = theCustomerServce.getCutomerById(id);
            cust.getBoughtCourses().remove(course);
            theCustomerServce.addOrUpdateCustomer(cust, theUserService);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /*
    Функционал для преподавателя
     */
    @Transactional
    public  List<Course> getAllCoursesForTeacher(Employee employee) {
        List<Course> teachersCourses = new ArrayList<>();
        for (Course crs:theCourseRepository.findAll()){
            if(crs.getMainTeacher().equals(employee))
                teachersCourses.add(crs);
        }
        return teachersCourses;
    }
    /*
    Функционал для пользователя для оперирования уже заготовленными подписками
     */

    @Transactional
    public List<CourseTopic> userGetAllTopics(Long user_id, Long courseId) {
        if (getPaidCourses(user_id).contains(theCourseRepository.getById(courseId))) {
            return new ArrayList<>(theCourseRepository.getById(courseId).getTopics());
        }
        return null;
    }

    @Transactional
    public CourseTopic userGetTopic(Long user_id, Long courseId, Long courseTopicId) {
        if (userGetAllTopics(user_id, courseId).contains(theCourseTopicRepository.getById(courseTopicId))) {
            return theCourseTopicRepository.getById(courseTopicId);
        }
        return null;
    }

    @Transactional
    public List<CourseQuestionInput> userGetInputQuestions(Long courseTopicId, Long count) {
        if (count <= 0) return null;
        ArrayList<CourseQuestionInput> courseQuestionInputs = new ArrayList<>(theCourseTopicRepository.getById(courseTopicId).getInputQ());
        if (courseQuestionInputs==null)return null;
        ArrayList<CourseQuestionInput> result = new ArrayList<>();
        Random rand = new Random();
        CourseQuestionInput randomElement = courseQuestionInputs.get(rand.nextInt(courseQuestionInputs.size()));
        courseQuestionInputs.remove(randomElement);
        result.add(randomElement);
        for (int i = 1; i < count; i++) {
            if (!courseQuestionInputs.isEmpty()) {
                randomElement = courseQuestionInputs.get(rand.nextInt(courseQuestionInputs.size()));
                courseQuestionInputs.remove(randomElement);
                result.add(randomElement);
            }
        }
        return result;
    }


    @Transactional
    public List<CourseQuestionSingle> userGetSingleQuestions(Long courseTopicId, Long count) {
        if (count <= 0) return null;
        ArrayList<CourseQuestionSingle> courseQuestionSingle = new ArrayList<>(theCourseTopicRepository.getById(courseTopicId).getSingleQ());
        if (courseQuestionSingle==null)return null;
        ArrayList<CourseQuestionSingle> result = new ArrayList<>();
        Random rand = new Random();
        CourseQuestionSingle randomElement = courseQuestionSingle.get(rand.nextInt(courseQuestionSingle.size()));
        courseQuestionSingle.remove(randomElement);
        result.add(randomElement);
        for (int i = 1; i < count; i++) {
            if (!courseQuestionSingle.isEmpty()) {
                randomElement = courseQuestionSingle.get(rand.nextInt(courseQuestionSingle.size()));
                courseQuestionSingle.remove(randomElement);
                result.add(randomElement);
            }
        }
        return result;
    }

    @Transactional
    public List<CourseQuestionMultiple> userGetMultipleQuestions(Long courseTopicId, Long count) {
        if (count <= 0) return null;
        ArrayList<CourseQuestionMultiple> courseQuestionMultiple = new ArrayList<>(theCourseTopicRepository.getById(courseTopicId).getMultipleQ());
        if (courseQuestionMultiple==null)return null;
        ArrayList<CourseQuestionMultiple> result = new ArrayList<>();
        Random rand = new Random();
        CourseQuestionMultiple randomElement = courseQuestionMultiple.get(rand.nextInt(courseQuestionMultiple.size()));
        courseQuestionMultiple.remove(randomElement);
        result.add(randomElement);
        for (int i = 1; i < count; i++) {
            if (!courseQuestionMultiple.isEmpty()) {
                randomElement = courseQuestionMultiple.get(rand.nextInt(courseQuestionMultiple.size()));
                courseQuestionMultiple.remove(randomElement);
                result.add(randomElement);
            }
        }
        return result;
    }


    /*
    Функционал по управлению топиками
     */
    @Transactional
    public List<CourseTopic> getAllTopics() {
        return theCourseTopicRepository.findAll();
    }

    @Transactional
    public CourseTopic getTopicById(Long id) {
        return theCourseTopicRepository.getById(id);
    }

    @Transactional
    public boolean deleteTopicById(Long id) {
        CourseTopic topic = getTopicById(id);
        if (topic == null)
            return false;
        for (Course temp : theCourseRepository.findAll()) {
            if (temp.getTopics().contains(topic)) {
                Set<CourseTopic> topics = temp.getTopics();
                topics.remove(topic);
                temp.setTopics(topics);
                theCourseRepository.save(temp);
                Set<CourseQuestionInput> inp = topic.getInputQ();
                Set<CourseQuestionMultiple> mult = topic.getMultipleQ();
                Set<CourseQuestionSingle> sing = topic.getSingleQ();
                Set<Answer> ansm = new HashSet<>();
                theCourseTopicRepository.delete(topic);
                inp.forEach(theCQInputRepository::delete);
                for (CourseQuestionMultiple tempM : mult) {
                    ansm.addAll(tempM.getAnswers());
                    ansm.addAll(tempM.getCorrectAnswers());
                    theCQMultipleRepository.delete(tempM);
                }
                for (CourseQuestionSingle tempM : sing) {
                    ansm.addAll(tempM.getAnswers());
                    ansm.add(tempM.getCorrectAnswer());
                    theCQSingleRepository.delete(tempM);
                }
                ansm.forEach(theAnswerRepository::delete);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean addTopicToCourse(CourseTopic theTopic, Long id) {
        try {
            Course courseById = getCourseById(id);
            courseById.addTopic(theTopic);
            theCourseRepository.save(courseById);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean justSave(CourseTopic theTopic) {
        try {
            theCourseTopicRepository.save(theTopic);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public boolean addOrUpdateTopic(CourseTopic theTopic) {
        try {
            if (theTopic.getId() == null)
                theCourseTopicRepository.save(theTopic);
            else {
                CourseTopic temp = theCourseTopicRepository.getById(theTopic.getId());
                temp.setName(theTopic.getName());
                temp.setTeacher(theTopic.getTeacher());
                temp.setContents(theTopic.getContents());
                theCourseTopicRepository.save(temp);
            }


        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Transactional
    public CourseTopic getTopicByInputQuestion(CourseQuestionInput q) {
        for (CourseTopic temp : theCourseTopicRepository.findAll()) {
            if (temp.getInputQ().contains(q)) return temp;
        }
        return null;
    }

    @Transactional
    public CourseTopic getTopicBySinleQuestion(CourseQuestionSingle q) {
        for (CourseTopic temp : theCourseTopicRepository.findAll()) {
            if (temp.getSingleQ().contains(q)) return temp;
        }
        return null;
    }

    @Transactional
    public CourseTopic getTopicByMultipleQuestion(CourseQuestionMultiple q) {
        for (CourseTopic temp : theCourseTopicRepository.findAll()) {
            if (temp.getMultipleQ().contains(q)) return temp;
        }
        return null;
    }


    /*
    Функционал по управлению курсами
     */
    @Transactional
    public List<Course> getAllCourses() {
        return theCourseRepository.findAll();
    }

    @Transactional
    public Course getCourseById(Long id) {
        return theCourseRepository.getById(id);
    }

    @Transactional
    public boolean deleteCourseById(Long id) {
        Set<CourseQuestionInput> inp;
        Set<CourseQuestionMultiple> mult;
        Set<CourseQuestionSingle> sing;
        Set<Answer> ansm = new HashSet<>();
        if (getCourseById(id) == null)
            return false;
        Set<CourseTopic> topics = getCourseById(id).getTopics();
        theCourseRepository.delete(getCourseById(id));
        for (CourseTopic temp : topics) {
            inp = temp.getInputQ();
            mult = temp.getMultipleQ();
            sing = temp.getSingleQ();

            theCourseTopicRepository.delete(temp);
            inp.forEach(theCQInputRepository::delete);
            for (CourseQuestionMultiple tempM : mult) {
                ansm.addAll(tempM.getAnswers());
                ansm.addAll(tempM.getCorrectAnswers());
                theCQMultipleRepository.delete(tempM);
            }
            for (CourseQuestionSingle tempM : sing) {
                ansm.addAll(tempM.getAnswers());
                ansm.add(tempM.getCorrectAnswer());
                theCQSingleRepository.delete(tempM);
            }
            ansm.forEach(theAnswerRepository::delete);
        }
        return true;
    }


    @Transactional
    public boolean addOrUpdateCourse(Course theCourse) {
        try {
            if (theCourse.getId() == null)
                theCourseRepository.save(theCourse);
            else {
                Course temp = theCourseRepository.getById(theCourse.getId());
                temp.setCost(theCourse.getCost());
                temp.setInfo(theCourse.getInfo());
                temp.setMainTeacher(theCourse.getMainTeacher());
                temp.setName(theCourse.getName());
                theCourseRepository.save(temp);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /*
    Раздел для работы с вопросами
     */
    @Transactional
    public boolean deleteQuestionById(long id, String qType) {
        CourseTopic temp = null;
        switch (qType) {
            case "input":
                CourseQuestionInput questI = theCQInputRepository.getById(id);
                if (questI == null) return false;
                temp = getTopicByInputQuestion(questI);
                temp.removeInputQ(questI);
                theCourseTopicRepository.save(temp);
                theCQInputRepository.delete(questI);
                return true;
            case "single":
                CourseQuestionSingle questS = theCQSingleRepository.getById(id);
                if (questS == null) return false;
                temp = getTopicBySinleQuestion(questS);
                temp.removeSingleQ(questS);
                theCourseTopicRepository.save(temp);
                theCQSingleRepository.delete(questS);
                return true;
            case "multiple":
                CourseQuestionMultiple questM = theCQMultipleRepository.getById(id);
                if (questM == null) return false;
                temp = getTopicByMultipleQuestion(questM);
                temp.removeMultipleQ(questM);
                theCourseTopicRepository.save(temp);
                theCQMultipleRepository.delete(questM);
                return true;
            default:
                return false;
        }
    }

    @Transactional
    public boolean questionExists(long id, String qType) {

        switch (qType) {
            case "input":
                return theCQInputRepository.getById(id) != null;
            case "single":
                return theCQSingleRepository.getById(id) != null;
            case "multiple":
                return theCQMultipleRepository.getById(id) != null;
            default:
                return false;
        }
    }

    @Transactional
    public CourseQuestionSingle getSingleQuestionById(long id) {
        return theCQSingleRepository.getById(id);
    }

    @Transactional
    public CourseQuestionInput getInputQuestionById(long id) {
        return theCQInputRepository.getById(id);
    }

    @Transactional
    public CourseQuestionMultiple getMultipleQuestionById(long id) {
        return theCQMultipleRepository.getById(id);
    }

    @Transactional
    public CourseQuestionInput addOrUpdateInputQuestion(CourseQuestionInput qu) {
        theCQInputRepository.save(qu);
        long max = 0;
        for (CourseQuestionInput t : theCQInputRepository.findAllByQuestion(qu.getQuestion()))
            if (t.getId() > max) max = t.getId();
        return theCQInputRepository.getById(max);
    }

    @Transactional
    public CourseQuestionSingle addOrUpdateSingleQuestion(CourseQuestionSingle qu) {
        theCQSingleRepository.save(qu);
        long max = 0;
        for (CourseQuestionSingle t : theCQSingleRepository.findAllByQuestion(qu.getQuestion()))
            if (t.getId() > max) max = t.getId();
        return theCQSingleRepository.getById(max);
    }

    @Transactional
    public CourseQuestionMultiple addOrUpdateMultipleQuestion(CourseQuestionMultiple qu) {
        theCQMultipleRepository.save(qu);
        long max = 0;
        for (CourseQuestionMultiple t : theCQMultipleRepository.findAllByQuestion(qu.getQuestion()))
            if (t.getId() > max) max = t.getId();
        return theCQMultipleRepository.getById(max);
    }

    @Transactional
    public Answer getAnswerById(long id) {
        return theAnswerRepository.getById(id);
    }

    @Transactional
    public Set<Answer> getAnswerByIds(String[] ids) {
        if (ids == null) return null;
        List<Answer> all = theAnswerRepository.findAll();
        Set<Answer> result = new HashSet<>();
        for (String id : ids) for (Answer ans : all) if (ans.getId().toString().equals(id)) result.add(ans);
        return result;
    }

    @Transactional
    public Answer addOrUpdateAnswer(Answer ans) {

        theAnswerRepository.save(ans);
        long max = 0;
        for (Answer answs : theAnswerRepository.findAllByContent(ans.getContent())) {
            if (answs.getId() > max) max = answs.getId();
        }

        return theAnswerRepository.getById(max);
    }

}
