package com.lang_learn_sys.main_app.course.questions.repo;

import com.lang_learn_sys.main_app.course.questions.entity.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long> {
    List<Answer> findAll();
    void deleteById(Long Id);
    Answer getById(Long id);
    List<Answer> findAllByContent(String content);
}


