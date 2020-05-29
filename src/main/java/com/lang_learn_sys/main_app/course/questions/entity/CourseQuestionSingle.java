package com.lang_learn_sys.main_app.course.questions.entity;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "crsquest_single")
public class CourseQuestionSingle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    @OneToMany
    private Set<Answer> answers;
    @OneToOne
    private Answer correctAnswer;
    @Transient
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getCorrectivity(Answer userAnswer){
        return correctAnswer.equals(userAnswer)?100:0;
    }

    public CourseQuestionSingle() {
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

    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Answer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
