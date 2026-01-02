package com.taskmanager.model;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueDate;
    private boolean isCompleted;

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    public Task(String title, String description, Priority priority, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Constructor for loading from file
    public Task(UUID id, String title, String description, Priority priority, LocalDate dueDate, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    @Override
    public String toString() {
        return title + " (" + priority + ") - " + dueDate + (isCompleted ? " [Completed]" : "");
    }
}
