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
cards left counter
Cat:

roadmap with cat items as rewards, Cat inventory
nice background and sprites
fix the sleeping bug

Shipping:

Logo
Name






 */