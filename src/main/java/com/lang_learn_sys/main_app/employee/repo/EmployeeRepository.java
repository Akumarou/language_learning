package com.lang_learn_sys.main_app.employee.repo;

import com.lang_learn_sys.main_app.employee.entity.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    List<Employee> findAll();
    void deleteById(Long Id);
    Optional<Employee> findById(Long id);
}