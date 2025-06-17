import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    SidebarPanel sidebar;
    MainPanel mainPanel;
    public AppWindow() {
        super("Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        mainPanel = new MainPanel();
        sidebar = new SidebarPanel(mainPanel);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }
}