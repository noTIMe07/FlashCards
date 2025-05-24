import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setBackground(new Color(232, 220, 200));
        setLayout(new GridBagLayout());

        JLabel welcome = new JLabel("Welcome to Flashcards!");
        welcome.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        welcome.setForeground(Style.ACCENT_COLOR);
        add(welcome);
    }
}