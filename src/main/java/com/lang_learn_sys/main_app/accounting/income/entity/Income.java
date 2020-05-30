package com.lang_learn_sys.main_app.accounting.income.entity;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;
import com.lang_learn_sys.main_app.customer.entity.Customer;
import com.lang_learn_sys.main_app.employee.entity.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private boolean closed;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private Set<Product> products;
    @Transient
    private Double totalCost = 0D;

    public Double getTotalCost() {
        this.totalCost = 0D;
        for(Product pr: this.products){
            this.totalCost +=Double.parseDouble(pr.getPrice())*pr.getCount();
        }
        return this.totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Income() {
        this.totalCost = 0D;
        this.products = new HashSet<>();
        this.customer = new Customer();
        this.customer.setFirstName("Default");
        this.customer.setLastName("Default");
        this.closed = false;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void deleteProd(Product prod) {
        products.remove(prod);
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}