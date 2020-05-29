package com.lang_learn_sys.main_app.customer.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="testres")
public class TestResult {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long course_id;
    private Long topic_id;
    private Date date;
    private Long result;

    public TestResult() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public Long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Long topic_id) {
        this.topic_id = topic_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getResult() {
        return result;
    }

    public void setResult(Long result) {
        this.result = result;
    }
}
