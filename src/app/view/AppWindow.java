package app.view;

import app.animation.AnimatedSprite;

import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    private JLayeredPane layeredPane;
    private JPanel contentHolder;
    private SidebarPanel sidebar;
    private MainPanel mainPanel;
    private AnimatedSprite animatedCat;

    public AppWindow() {
        super("Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // JPanel to hold main content
        contentHolder = new JPanel();
        contentHolder.setOpaque(false);
        contentHolder.setBounds(0, 0, 1920, 1080);
        contentHolder.setLayout(new BorderLayout());

        animatedCat = new AnimatedSprite(0, 0);
        animatedCat.setBounds(0, 0, 1920, 1080);

        mainPanel = new MainPanel(animatedCat);
        sidebar = new SidebarPanel(mainPanel);

        contentHolder.add(sidebar, BorderLayout.WEST);
        contentHolder.add(mainPanel, BorderLayout.CENTER);

        // Cat animation above content
        layeredPane = new AutoLayoutLayeredPane(contentHolder, animatedCat);

        add(layeredPane, BorderLayout.CENTER);
    }
}