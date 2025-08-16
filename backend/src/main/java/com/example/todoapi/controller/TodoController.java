package com.example.todoapi.controller;

import com.example.todoapi.entity.Todo;
import com.example.todoapi.repository.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Todo", description = "Cat Todo API for managing tasks")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    @Operation(summary = "Get all todos", description = "Retrieve a list of all todo items")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todo list")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID", description = "Retrieve a specific todo item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo found"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<Todo> getTodoById(
            @Parameter(description = "ID of the todo to retrieve") @PathVariable Long id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        
        if (todoOptional.isPresent()) {
            return ResponseEntity.ok(todoOptional.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create a new todo", description = "Create a new todo item")
    @ApiResponse(responseCode = "200", description = "Todo created successfully")
    public Todo createTodo(
            @Parameter(description = "Todo object to be created") @RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a todo", description = "Update an existing todo item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo updated successfully"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<Todo> updateTodo(
            @Parameter(description = "ID of the todo to update") @PathVariable Long id,
            @Parameter(description = "Updated todo data") @RequestBody Todo todoDetails) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        
        if (todoOptional.isPresent()) {
            Todo todo = todoOptional.get();
            if (todoDetails.getTitle() != null) {
                todo.setTitle(todoDetails.getTitle());
            }
            if (todoDetails.getDescription() != null) {
                todo.setDescription(todoDetails.getDescription());
            }
            if (todoDetails.getCompleted() != null) {
                todo.setCompleted(todoDetails.getCompleted());
            }
            if (todoDetails.getDueDate() != null) {
                todo.setDueDate(todoDetails.getDueDate());
            }
            return ResponseEntity.ok(todoRepository.save(todo));
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a todo", description = "Delete a todo item by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public ResponseEntity<Void> deleteTodo(
            @Parameter(description = "ID of the todo to delete") @PathVariable Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}