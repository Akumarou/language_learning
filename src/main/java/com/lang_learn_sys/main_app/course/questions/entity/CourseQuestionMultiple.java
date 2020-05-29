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
    @Transient
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public CourseQuestionMultiple() {
    }
    public int getCorrectivity(Set<Answer> userAnswers){
        if(userAnswers==null)return 0;
        if (correctAnswers.equals(userAnswers))
            return 100;
        float cost = (100+0F) / (answers.size()+0F);
        float result = 0F;
        for (Answer ans:userAnswers){
            if(correctAnswers.contains(ans))result+=cost;
            else result-=cost;
        }
        return (result<0F)?0:Math.round(result);
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
