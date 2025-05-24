import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class Folder extends JPanel {
    private static int count = 1;

    public Folder(Color bgColor, Color outline, Font font) {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 80));
        setBackground(bgColor);
        setBorder(new CompoundBorder(new LineBorder(outline, 2, true), new EmptyBorder(10, 15, 10, 15)));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("New Folder"+count++, UIManager.getIcon("FileView.directoryIcon"), JLabel.LEFT);
        nameLabel.setFont(font);
        nameLabel.setForeground(Color.DARK_GRAY);

        add(nameLabel, BorderLayout.CENTER);
    }
}
