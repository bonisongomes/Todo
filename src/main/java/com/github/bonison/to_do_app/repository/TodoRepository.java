package com.github.bonison.to_do_app.repository;

import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {
    List<Todo> findByUser(User user);
}
