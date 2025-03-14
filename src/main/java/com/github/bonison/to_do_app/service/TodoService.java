package com.github.bonison.to_do_app.service;

import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.repository.TodoRepository;
import com.github.bonison.to_do_app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    public boolean deleteTodo(long id,String username){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            Todo todo1 = todo.get();
            if (todo1.getUser().getUsername().equals(username)){
                todoRepository.delete(todo1);
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean updateTodo(String username, long id , Todo updatedTodo){
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if(optionalTodo.isPresent()){
            Todo extracted_todo = optionalTodo.get();
            if (extracted_todo.getUser().getUsername().equals(username)){
                extracted_todo.setTitle(updatedTodo.getTitle());
                extracted_todo.setDescription(updatedTodo.getDescription());
                extracted_todo.setCompleted(updatedTodo.isCompleted());
                todoRepository.save(extracted_todo);
                return true;
            }
        }
        return false;
    }
}
