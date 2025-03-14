package com.github.bonison.to_do_app.controller;

import com.github.bonison.to_do_app.config.JwtUtil;
import com.github.bonison.to_do_app.model.Todo;
import com.github.bonison.to_do_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getTodos(@RequestHeader("Authorization") String token){
        String username = jwtUtil.extractUsername(token.substring(7));
        List<Todo> todos = todoService.getAllToDoForUser(username);
        if(!todos.isEmpty()){
            return new ResponseEntity<>(todos, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestHeader("Authorization") String token, @RequestBody Todo todo){
        try {
            String username = jwtUtil.extractUsername(token.substring(7));
            return new ResponseEntity<>(todoService.createTodo(username,todo),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@RequestHeader("Authorization") String token, @PathVariable long id){
        String username = jwtUtil.extractUsername(token.substring(7));
        boolean res = todoService.deleteTodo(id,username);
        if (res == true) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@RequestHeader("Authorization") String token , @PathVariable long id ,
                                           @RequestBody Todo updatedTodo){
        String username = jwtUtil.extractUsername(token.substring(7));
        boolean result = todoService.updateTodo(username, id , updatedTodo);
        if (result == true){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
