package app.view;

import app.animation.AnimatedSprite;

import javax.swing.*;
import java.awt.*;


public class AppWindow extends JFrame {
    private CenterLayoutLP centerLayoutLP;
    private SidebarPanel sidebarPanel;

    public AppWindow() {
        super("Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        centerLayoutLP = new CenterLayoutLP();
        sidebarPanel = new SidebarPanel(centerLayoutLP);
        add(sidebarPanel, BorderLayout.WEST);
        add(centerLayoutLP, BorderLayout.CENTER);
    }
}