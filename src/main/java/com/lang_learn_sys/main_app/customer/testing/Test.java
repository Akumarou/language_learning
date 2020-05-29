package com.lang_learn_sys.main_app.customer.testing;

import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionInput;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionMultiple;
import com.lang_learn_sys.main_app.course.questions.entity.CourseQuestionSingle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    private long user_id;
    private long course_id;
    private long topic_id;
    private CourseQuestionMultiple[] qMultiple;
    private CourseQuestionSingle[] qSingle;
    private CourseQuestionInput[] qInput;


    public Test() {
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public CourseQuestionMultiple[] getqMultiple() {
        return qMultiple;
    }

    public void setqMultiple(List<CourseQuestionMultiple> questions) {
        if (questions != null){
            this.qMultiple = questions.toArray(new CourseQuestionMultiple[questions.size()]);
            for (int i=0;i<questions.size();i++)
                this.qMultiple[i].setOrder(i);
        }
    }

    public CourseQuestionSingle[] getqSingle() {
        return qSingle;
    }

    public void setqSingle(List<CourseQuestionSingle> questions) {
        if (questions != null){
            this.qSingle = questions.toArray(new CourseQuestionSingle[questions.size()]);
            for (int i=0;i<questions.size();i++)
                this.qSingle[i].setOrder(i);
        }
    }

    public CourseQuestionInput[] getqInput() {
        return qInput;
    }

    public void setqInput(List<CourseQuestionInput> questions) {
        if (questions != null){
            this.qInput = questions.toArray(new CourseQuestionInput[questions.size()]);
        }
    }

    @Override
    public String toString() {
        return "Test{" +
                "user_id=" + user_id +
                ", course_id=" + course_id +
                ", topic_id=" + topic_id +
                ", qMultiple=" + Arrays.toString(qMultiple) +
                ", qSingle=" + Arrays.toString(qSingle) +
                ", qInput=" + Arrays.toString(qInput) +
                '}';
    }
}
