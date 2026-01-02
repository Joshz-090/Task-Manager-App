package com.taskmanager;

import com.taskmanager.gui.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel to System default for better integration
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
