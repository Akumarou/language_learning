package com.lang_learn_sys.main_app.course.questions.repo;

import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionMultiple;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CQMultipleRepository  extends CrudRepository<CourseQuestionMultiple,Long> {
    List<CourseQuestionMultiple> findAll();
    void deleteById(Long Id);
    CourseQuestionMultiple getById(Long id);

    List<CourseQuestionMultiple> findAllByQuestion(String question);
}

