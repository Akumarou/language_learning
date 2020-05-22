package com.lang_learn_sys.main_app.accounting.income.repo;

import com.lang_learn_sys.main_app.accounting.income.entity.Income;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeRepository extends CrudRepository<Income,Long> {
    List<Income> findAll();
    void deleteById(Long Id);
    Income getById(Long id);
}