package com.lang_learn_sys.main_app.security.repo;

import com.lang_learn_sys.main_app.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
