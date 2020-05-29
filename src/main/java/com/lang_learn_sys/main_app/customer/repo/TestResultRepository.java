package com.lang_learn_sys.main_app.customer.repo;

import com.lang_learn_sys.main_app.customer.entity.TestResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestResultRepository extends CrudRepository<TestResult,Long> {
    List<TestResult> findAll();
    void deleteById(Long Id);
    TestResult getById(Long id);
}
