package com.github.bonison.to_do_app.service;

import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.repository.TodoRepository;
import com.github.bonison.to_do_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Todo> getAllToDoForUser(String username){
        User user = userRepository.findByUsername(username);
        return todoRepository.findByUser(user);
    }
    public Todo createTodo(String username, Todo todo){
        User user = userRepository.findByUsername(username);
        todo.setUser(user);
        return todoRepository.save(todo);
    }
    public void deleteTodo(long id){
        todoRepository.deleteById(id);
    }
}
