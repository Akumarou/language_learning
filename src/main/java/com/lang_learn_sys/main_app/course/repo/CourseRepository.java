package com.lang_learn_sys.main_app.course.repo;

import com.lang_learn_sys.main_app.course.entity.Course;
import com.lang_learn_sys.main_app.course.entity.CourseTopic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    List<Course> findAll();

    void deleteById(Long Id);

    Course getById(Long id);
}