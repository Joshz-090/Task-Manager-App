package com.taskmanager.gui;

import com.taskmanager.model.Task;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Title", "Priority", "Due Date", "Status"};
    private List<Task> tasks;

    public TaskTableModel(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        fireTableDataChanged();
    }

    public Task getTaskAt(int rowIndex) {
        return tasks.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Task task = tasks.get(rowIndex);
        switch (columnIndex) {
            case 0: return task.getTitle();
            case 1: return task.getPriority();
            case 2: return task.getDueDate();
            case 3: return task.isCompleted() ? "Completed" : "Pending";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
