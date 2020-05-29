package com.lang_learn_sys.main_app.customer.entity;

import com.lang_learn_sys.main_app.course.entity.Course;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToMany
    private Set<Course> boughtCourses;
    @OneToMany
    private Set<TestResult> successes;
    public void addSuccess(TestResult temp){
        this.successes.add(temp);
    }
    public void removeSuccess(TestResult temp){
        this.successes.remove(temp);
    }
    public Set<TestResult> getSuccesses() {
        return successes;
    }

    public void setSuccesses(Set<TestResult> successes) {
        this.successes = successes;
    }

    public Set<Course> getBoughtCourses() {
        return boughtCourses;
    }

    public void setBoughtCourses(Set<Course> boughtCourses) {
        this.boughtCourses = boughtCourses;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
