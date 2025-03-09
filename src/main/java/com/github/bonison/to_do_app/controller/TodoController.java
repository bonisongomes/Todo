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
    public ResponseEntity<?> deleteTodo(@RequestHeader("Authorization") String token, @PathVariable long id){
        String username = jwtUtil.extractUsername(token.substring(7));
        String result = todoService.deleteTodo(id,username);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@RequestHeader("Authorization") String token , @PathVariable long id ,
                                           @RequestBody Todo updatedTodo){
        String username = jwtUtil.extractUsername(token.substring(7));
        String result = todoService.updateTodo(username, id , updatedTodo);
        return ResponseEntity.ok().body(result);
    }
}
