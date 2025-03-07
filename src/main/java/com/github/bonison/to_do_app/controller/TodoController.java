package com.github.bonison.to_do_app.controller;

import com.github.bonison.to_do_app.config.JwtUtil;
import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin
public class TodoController {

    @Autowired
    public TodoService todoService;

    @Autowired
    public JwtUtil jwtUtil;

    @GetMapping
    public List<Todo> getTodos(@RequestHeader("Authorization") String token){
        String username = jwtUtil.extractUsername(token.substring(7));
        return todoService.getAllToDoForUser(username);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestHeader("Authorization") String token, @RequestBody Todo todo){
        String username = jwtUtil.extractUsername(token.substring(7));
        return ResponseEntity.ok(todoService.createTodo(username,todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok().build();
    }
}
