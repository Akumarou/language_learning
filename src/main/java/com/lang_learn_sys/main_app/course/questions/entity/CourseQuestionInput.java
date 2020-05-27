package com.lang_learn_sys.main_app.course.questions.entity;

import com.lang_learn_sys.main_app.course.entity.CourseTopic;

import javax.persistence.*;

@Entity
@Table(name = "crsquest_input")
public class CourseQuestionInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String question;
    private String correctAnswer;

    public int getCorrectivity(String userAnswer){
        if (userAnswer.equals(correctAnswer)) return 100;
        else if (correctAnswer.contains(userAnswer)) return 50;
        else return 0;
    }

    public CourseQuestionInput(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }


    public CourseQuestionInput() {
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

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String answer) {
        this.correctAnswer = answer;
    }
}