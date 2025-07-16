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



//double click folder to rename
//click folder to show default panel