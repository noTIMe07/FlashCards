import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FolderPanel extends JPanel {
    public FolderPanel() {
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(240, 50));
        setBackground(Style.FOLDER_COLOR);
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, true),
                new EmptyBorder(10, 15, 10, 15)
        ));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel("New Folder", UIManager.getIcon("FileView.directoryIcon"), JLabel.LEFT);
        nameLabel.setFont(Style.FOLDER_FONT);
        nameLabel.setForeground(Color.DARK_GRAY);
        add(nameLabel, BorderLayout.CENTER);
    }
}
