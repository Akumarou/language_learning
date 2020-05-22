package com.lang_learn_sys.main_app.accounting.writeoff.entity;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.employee.entity.Employee;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "writeoff")
public class Writeoff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private boolean closed;
    @ManyToMany
    private Set<Employee> employees;
    @OneToMany
    private Set<Product> products;
    @Transient
    private Double totalCost = 0D;

    public Double getTotalCost() {
        this.totalCost = 0D;
        for(Product pr: this.products){
            this.totalCost +=Double.parseDouble(pr.getPrice()) - (Double.parseDouble(pr.getPrice()) * Double.parseDouble(pr.getSale()) / 100);
        }
        return this.totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Writeoff() {
        this.totalCost = 0D;
        this.products = new HashSet<>();
        this.employees = new HashSet<>();
        this.closed = false;
    }

    public void deleteEmpl(Employee empl) {
        employees.remove(empl);
    }

    public void deleteProd(Product prod) {
        products.remove(prod);
    }
    public void addEmpl(Employee empl) {
        employees.add(empl);
    }

    public void addProd(Product prod) {
        products.add(prod);
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}