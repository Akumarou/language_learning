package com.lang_learn_sys.main_app.accounting.writeoff.repo;

import com.lang_learn_sys.main_app.accounting.writeoff.entity.Writeoff;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WriteoffRepository extends CrudRepository<Writeoff,Long> {
    List<Writeoff> findAll();
    void deleteById(Long Id);
    Writeoff getById(Long id);
}