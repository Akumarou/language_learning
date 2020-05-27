package com.lang_learn_sys.main_app.course.questions.repo;

import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionSingle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CQSingleRepository extends CrudRepository<CourseQuestionSingle,Long> {
    List<CourseQuestionSingle> findAll();
    void deleteById(Long Id);
    CourseQuestionSingle getById(Long id);

    List<CourseQuestionSingle>  findAllByQuestion(String question);
}
