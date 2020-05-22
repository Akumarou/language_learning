package com.lang_learn_sys.main_app.accounting.provider.repo;

import com.lang_learn_sys.main_app.accounting.provider.entity.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProviderRepository extends CrudRepository<Provider,Long> {
    List<Provider> findAll();
    void deleteById(Long Id);
    Provider getById(Long id);
}
