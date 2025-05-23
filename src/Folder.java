import javax.swing.*;
import java.awt.*;

public class Folder extends JPanel {
    private static int count = 1;

    public Folder() {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setBackground(new Color(230, 230, 250)); // light lavender

        JLabel nameLabel = new JLabel("Folder " + count++);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        add(nameLabel, BorderLayout.CENTER);
    }
}
