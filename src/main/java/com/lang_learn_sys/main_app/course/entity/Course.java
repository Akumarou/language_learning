package com.lang_learn_sys.main_app.course.entity;

import com.lang_learn_sys.main_app.employee.entity.Employee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "crsmain")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private Set<CourseTopic> topics;
    @OneToOne
    private Employee mainTeacher;
    private String name;
    private String cost;
    private String info;

    public int getCountOfCourses() {
        return topics.size();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Set<CourseTopic> getTopics() {
        return topics;
    }


    public void setTopics(Set<CourseTopic> topics) {
        this.topics = topics;
    }

    public void addTopic(CourseTopic temp) {
        this.topics.add(temp);
    }

    public Employee getMainTeacher() {
        return mainTeacher;
    }

    public void setMainTeacher(Employee mainTeacher) {
        this.mainTeacher = mainTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Course() {
    }
}
