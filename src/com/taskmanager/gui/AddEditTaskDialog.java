package com.taskmanager.gui;

import com.taskmanager.model.Task;
import com.taskmanager.model.Task.Priority;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class AddEditTaskDialog extends JDialog {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<Priority> priorityComboBox;
    private JTextField dateField;
    private JCheckBox completedCheckBox;
    private boolean isConfirmed = false;
    private Task resultTask;

    public AddEditTaskDialog(Frame owner, Task taskToEdit) {
        super(owner, taskToEdit == null ? "Add New Task" : "Edit Task", true);
        setLayout(new BorderLayout());
        setSize(400, 350);
        setLocationRelativeTo(owner);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        // Description
        inputPanel.add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        descriptionArea.setRows(3);
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        // We need to add the scroll pane, but grid layout might be weird with it. 
        // For simplicity in Grid, let's just use the field directly or fix layout later.
        // Actually, let's just put it in the grid.
        inputPanel.add(descScroll); 
        // Note: GridLayout will force all cells to be same size, so TextArea might be small. 
        // Ideally we use GridBagLayout, but for simplicity/speed...
        // Let's stick to GridLayout and see. If it looks bad, we can fix.
        // wait, adding JScrollPane to proper grid cell is fine.

        // Priority
        inputPanel.add(new JLabel("Priority:"));
        priorityComboBox = new JComboBox<>(Priority.values());
        inputPanel.add(priorityComboBox);

        // Date
        inputPanel.add(new JLabel("Due Date (YYYY-MM-DD):"));
        dateField = new JTextField(LocalDate.now().toString());
        inputPanel.add(dateField);

        // Completed (only if editing)
        inputPanel.add(new JLabel("Completed:"));
        completedCheckBox = new JCheckBox();
        inputPanel.add(completedCheckBox);

        add(inputPanel, BorderLayout.CENTER);

        // Pre-fill if editing
        if (taskToEdit != null) {
            titleField.setText(taskToEdit.getTitle());
            descriptionArea.setText(taskToEdit.getDescription());
            priorityComboBox.setSelectedItem(taskToEdit.getPriority());
            dateField.setText(taskToEdit.getDueDate().toString());
            completedCheckBox.setSelected(taskToEdit.isCompleted());
        } else {
            // Defaults
            priorityComboBox.setSelectedItem(Priority.MEDIUM);
            completedCheckBox.setEnabled(false); // Can't start as completed usually? Or maybe allowed. Le'ts disable for new.
            completedCheckBox.setSelected(false);
        }

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            if (validateInput()) {
                isConfirmed = true;
                UUID id = (taskToEdit != null) ? taskToEdit.getId() : UUID.randomUUID();
                String title = titleField.getText().trim();
                String desc = descriptionArea.getText().trim();
                Priority prio = (Priority) priorityComboBox.getSelectedItem();
                LocalDate date = LocalDate.parse(dateField.getText().trim());
                boolean completed = completedCheckBox.isSelected();

                resultTask = new Task(id, title, desc, prio, date, completed);
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validateInput() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            LocalDate.parse(dateField.getText().trim());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid Date format. Use YYYY-MM-DD.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public Task getResultTask() {
        return resultTask;
    }
}
