package com.taskmanager.logic;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.Priority;
import com.taskmanager.util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
        loadTasks();
    }

    public void loadTasks() {
        try {
            this.tasks = FileHandler.loadTasks();
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            this.tasks = new ArrayList<>();
        }
    }

    public void saveTasks() {
        try {
            FileHandler.saveTasks(this.tasks);
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasks();
    }

    public void updateTask(Task updatedTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(updatedTask.getId())) {
                tasks.set(i, updatedTask);
                break;
            }
        }
        saveTasks();
    }

    public void deleteTask(Task task) {
        tasks.removeIf(t -> t.getId().equals(task.getId()));
        saveTasks();
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // --- Extra Features: Search, Filter, Sort ---

    public List<Task> searchTasks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllTasks();
        }
        String lowerQuery = query.toLowerCase();
        return tasks.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(lowerQuery) || 
                             t.getDescription().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    public List<Task> filterByPriority(Priority priority) {
        if (priority == null) return getAllTasks();
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    public List<Task> filterByStatus(Boolean isCompleted) {
        if (isCompleted == null) return getAllTasks();
        return tasks.stream()
                .filter(t -> t.isCompleted() == isCompleted)
                .collect(Collectors.toList());
    }

    public void sortTasksByDate() {
        tasks.sort(Comparator.comparing(Task::getDueDate));
    }

    public void sortTasksByPriority() {
        tasks.sort(Comparator.comparing(Task::getPriority).reversed()); // High to Low
    }
    
    public int getTotalTasks() {
        return tasks.size();
    }
    
    public int getCompletedTasks() {
        return (int) tasks.stream().filter(Task::isCompleted).count();
    }
}
