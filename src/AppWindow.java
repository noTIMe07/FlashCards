import javax.swing.*;
import java.awt.*;

public class AppWindow extends JFrame {
    public AppWindow() {
        super("Flashcard App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        SidebarPanel sidebar = new SidebarPanel();
        MainPanel mainPanel = new MainPanel();

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }
}