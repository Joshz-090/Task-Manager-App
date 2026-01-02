package com.taskmanager.util;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.Priority;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileHandler {
    private static final String FILE_NAME = "tasks_data.csv";
    private static final String SEPARATOR = "\\|";
    private static final String SEPARATOR_CHAR = "|";

    public static void saveTasks(List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                // Sanitize content to avoid separator conflict
                String title = sanitize(task.getTitle());
                String description = sanitize(task.getDescription());
                
                String line = String.join(SEPARATOR_CHAR,
                        task.getId().toString(),
                        title,
                        description,
                        task.getPriority().name(),
                        task.getDueDate().toString(),
                        String.valueOf(task.isCompleted())
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(SEPARATOR);
                if (parts.length >= 6) {
                    try {
                        UUID id = UUID.fromString(parts[0]);
                        String title = desanitize(parts[1]);
                        String description = desanitize(parts[2]);
                        Priority priority = Priority.valueOf(parts[3]);
                        LocalDate dueDate = LocalDate.parse(parts[4]);
                        boolean isCompleted = Boolean.parseBoolean(parts[5]);

                        tasks.add(new Task(id, title, description, priority, dueDate, isCompleted));
                    } catch (IllegalArgumentException | DateTimeParseException e) {
                        System.err.println("Skipping corrupted line: " + line);
                        // Using System.err is acceptable for logging in this scope
                    }
                }
            }
        }
        return tasks;
    }

    private static String sanitize(String input) {
        if (input == null) return "";
        // Replace pipe and newlines to keep single line format
        return input.replace("|", "%PIPE%").replace("\n", "%NL%");
    }

    private static String desanitize(String input) {
        if (input == null) return "";
        return input.replace("%PIPE%", "|").replace("%NL%", "\n");
    }
}
