package com.lang_learn_sys.main_app.course.questions.entity;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "crsquest_multiple")
public class CourseQuestionMultiple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @OneToMany
    private Set<Answer> answers;
    @OneToMany
    private Set<Answer> correctAnswers;

    public CourseQuestionMultiple() {
    }
    public int getCorrectivity(Set<Answer> userAnswers){
        if (correctAnswers.containsAll(userAnswers))
            return 100;
        else
            return 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Answer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Set<Answer> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
