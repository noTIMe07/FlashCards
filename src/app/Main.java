package app;

import app.view.AppWindow;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.0");

        SwingUtilities.invokeLater(() -> {
            AppWindow window = new AppWindow();
            window.setVisible(true);
        });
    }
}

/*
Functionality:

click folder to show default panel
double click folder to rename

Cat:

When flashcard is shown then action buttons, else cat features
roadmap with cat items as rewards
nice background and sprtes
fix the sleeping bug

Shipping:

Logo
Name






 */