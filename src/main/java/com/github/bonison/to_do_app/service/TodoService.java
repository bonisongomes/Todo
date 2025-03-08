package com.github.bonison.to_do_app.service;

import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.model.User;
import com.github.bonison.to_do_app.repository.TodoRepository;
import com.github.bonison.to_do_app.repository.UserRepository;
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
    public String deleteTodo(long id,String username){
        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isPresent()){
            Todo todo1 = todo.get();
            if (todo1.getUser().getUsername().equals(username)){
                todoRepository.delete(todo1);
                return "Todo deleted successfully";
            }
        }
        return "Todo for given Id is not present or you are not allowed to delete this Todo";
    }
}
