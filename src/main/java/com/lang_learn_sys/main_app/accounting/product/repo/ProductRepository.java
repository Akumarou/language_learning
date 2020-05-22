package com.lang_learn_sys.main_app.accounting.product.repo;

import com.lang_learn_sys.main_app.accounting.product.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    List<Product> findAll();
    void deleteById(Long Id);
    Product getById(Long id);
}
