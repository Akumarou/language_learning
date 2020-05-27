package com.lang_learn_sys.main_app.course.questions.repo;

import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CQInputRepository extends CrudRepository<CourseQuestionInput,Long> {
    List<CourseQuestionInput> findAll();
    void deleteById(Long Id);
    CourseQuestionInput getById(Long id);
    List<CourseQuestionInput> findAllByQuestion(String q);
}
