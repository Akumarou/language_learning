package com.lang_learn_sys.main_app.course.entity;

import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionMultiple;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionSingle;
import com.lang_learn_sys.main_app.employee.entity.Employee;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "crstopic")
public class CourseTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contents;
    @OneToOne
    private Employee teacher;
    @OneToMany
    private Set<CourseQuestionInput> inputQ;
    @OneToMany
    private Set<CourseQuestionSingle> singleQ;
    @OneToMany
    private Set<CourseQuestionMultiple> multipleQ;

    public CourseTopic() {
    }
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getTeacher() {
        return teacher;
    }

    public void setTeacher(Employee teacher) {
        this.teacher = teacher;
    }

    public Set<CourseQuestionInput> getInputQ() {
        return inputQ;
    }

    public void setInputQ(Set<CourseQuestionInput> inputQ) {
        this.inputQ = inputQ;
    }

    public void removeInputQ(CourseQuestionInput inputQ1) {
        this.inputQ.remove(inputQ1);
    }

    public void removeSingleQ(CourseQuestionSingle inputQ1) {
        this.singleQ.remove(inputQ1);
    }

    public void removeMultipleQ(CourseQuestionMultiple inputQ1) {
        this.multipleQ.remove(inputQ1);
    }

    public void addInputQ(CourseQuestionInput inputQ1) {
        this.inputQ.add(inputQ1);
    }

    public void addSingleQ(CourseQuestionSingle inputQ1) {
        this.singleQ.add(inputQ1);
    }

    public void addMultipleQ(CourseQuestionMultiple inputQ1) {
        this.multipleQ.add(inputQ1);
    }

    public Set<CourseQuestionSingle> getSingleQ() {
        return singleQ;
    }

    public void setSingleQ(Set<CourseQuestionSingle> singleQ) {
        this.singleQ = singleQ;
    }

    public Set<CourseQuestionMultiple> getMultipleQ() {
        return multipleQ;
    }

    public void setMultipleQ(Set<CourseQuestionMultiple> multipleQ) {
        this.multipleQ = multipleQ;
    }
}
