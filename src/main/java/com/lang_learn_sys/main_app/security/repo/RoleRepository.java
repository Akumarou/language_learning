package com.lang_learn_sys.main_app.security.repo;

import com.lang_learn_sys.main_app.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getById(Long id);
    List<Role> findAll();
    void deleteById(Long Id);
}
