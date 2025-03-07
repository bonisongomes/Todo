package com.github.bonison.to_do_app.repository;

import com.github.bonison.to_do_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
