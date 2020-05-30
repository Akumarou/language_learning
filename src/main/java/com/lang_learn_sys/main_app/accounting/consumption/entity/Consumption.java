package com.lang_learn_sys.main_app.accounting.consumption.entity;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import com.lang_learn_sys.main_app.accounting.provider.entity.Provider;
import com.lang_learn_sys.main_app.customer.entity.Customer;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consumption")
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private boolean closed;
    @ManyToOne
    private Provider provider;
    @OneToMany
    private Set<Product> products;
    @Transient
    private Double totalCost = 0D;

    public Consumption() {
        this.totalCost = 0D;
        this.products = new HashSet<>();
        this.provider = new Provider();
        this.closed = false;
    }
    public Double getTotalCost() {
        this.totalCost = 0D;
        for(Product pr: this.products){
            this.totalCost +=Double.parseDouble(pr.getPrice())*pr.getCount();
        }
        return this.totalCost;
    }

    public void deleteProd(Product prod) {
        products.remove(prod);
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

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }
}