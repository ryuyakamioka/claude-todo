package com.example.todoapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
@Schema(description = "Todo item entity")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the todo", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Title of the todo item", example = "Buy cat food", required = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed description of the todo item", example = "Get premium dry food for Luna")
    private String description;

    @Column(nullable = false)
    @Schema(description = "Completion status of the todo", example = "false", required = true)
    private Boolean completed = false;

    @Column(name = "created_at", nullable = false)
    @Schema(description = "Timestamp when the todo was created", example = "2025-08-16T10:00:00")
    private LocalDateTime createdAt;

    public Todo() {
        this.createdAt = LocalDateTime.now();
    }

    public Todo(String title) {
        this();
        this.title = title;
    }

    public Todo(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}