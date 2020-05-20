package com.lang_learn_sys.main_app.customer.repo;

import com.lang_learn_sys.main_app.customer.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    List<Customer> findAll();
    void deleteById(Long Id);
    Optional<Customer> findById(Long id);
}
