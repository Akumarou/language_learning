package com.lang_learn_sys.main_app.security.repo;

import com.lang_learn_sys.main_app.security.entity.Role;
import com.lang_learn_sys.main_app.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User getById(Long id);

}
