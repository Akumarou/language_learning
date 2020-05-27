package com.lang_learn_sys.main_app.course.repo;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CourseTopicRepository  extends CrudRepository<CourseTopic, Long> {
    List<CourseTopic> findAll();

    void deleteById(Long Id);

    CourseTopic getById(Long id);
}
