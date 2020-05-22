package com.lang_learn_sys.main_app.accounting.consumption.repo;

import com.lang_learn_sys.main_app.accounting.consumption.entity.Consumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption,Long> {
    List<Consumption> findAll();
    void deleteById(Long Id);
    Consumption getById(Long id);
}
