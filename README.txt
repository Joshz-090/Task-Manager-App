# Task Manager Application - Java Swing Project

## Overview
This is a Java Swing-based application for managing tasks. It allows users to:
- Add, Edit, Delete tasks.
- Mark tasks as completed.
- Save and Load tasks automatically to/from `tasks_data.csv`.
- Search, Filter, and Sort tasks.
- View real-time statistics.
- Toggle between Light and Dark themes.

## How to Run

### Method 1: Runnable JAR (Recommended)
Double-click the `TaskManager.jar` file.
OR run from command line:
`java -jar TaskManager.jar`

### Method 2: Compile and Run from Source
1. Compile:
   `javac -d bin src/com/taskmanager/model/*.java src/com/taskmanager/util/*.java src/com/taskmanager/logic/*.java src/com/taskmanager/gui/*.java src/com/taskmanager/Main.java`
2. Run:
   `java -cp bin com.taskmanager.Main`

## Features
- **Persistence**: Tasks are saved to `tasks_data.csv` in a human-readable format.
- **Validation**: Ensures tasks have titles and valid dates.
- **Sorting**: Sort by Due Date or Priority.
- **Filtering**: Search by text or filter by Priority (via code logic, UI simplifies to Search/Sort).
- **Statistics**: Shows total and completed count.
- **Dark Mode**: Toggle button for "Dark Mode".

## Roles
- Group Member 1: Core Logic (Task, FileHandler)
- Group Member 2: GUI Implementation (MainFrame, Dialog)
- Group Member 3: Testing and Integration

## Notes
- The "Dark Mode" is a simple custom implementation changing component colors.
- Dates must be in YYYY-MM-DD format.
