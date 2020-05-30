package com.lang_learn_sys.main_app.accounting.product.entity;

import com.lang_learn_sys.main_app.accounting.product_info.entity.ProductInfo;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private ProductInfo prodinfo;
    private long count;


    //будем иметь ссылку на prodinfo


    //и скидку обязательно, а может и нет
    private String sale;

    public Product() {
        this.count=0;
    }

    public Product(ProductInfo prodinfo, long count) {
        this.prodinfo = prodinfo;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return prodinfo.getName();
    }

    public String getDescr() {
        return prodinfo.getDescr();
    }

    public String getPrice() {
        return prodinfo.getPrice();
    }

    public String getSale() {
        return sale;
    }

    public ProductInfo getProdinfo() {
        return prodinfo;
    }

    public void setProdinfo(ProductInfo prodinfo) {
        this.prodinfo = prodinfo;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", prodinfo=" + prodinfo +
                ", count=" + count +
                ", sale='" + sale + '\'' +
                '}';
    }
}
