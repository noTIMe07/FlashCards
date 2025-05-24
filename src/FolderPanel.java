import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class FolderPanel extends JPanel {
    public FolderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(200, 60));
        setBackground(Style.FOLDER_COLOR);
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 15, 10, 15)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("New Folder", JLabel.LEFT);
        nameLabel.setFont(Style.FOLDER_FONT);
        nameLabel.setForeground(Color.DARK_GRAY);
        add(nameLabel);

        //Space in the middle
        add(Box.createHorizontalGlue());

        //Recycle Bin Icon
        ImageIcon recycleIcon = new ImageIcon(getClass().getResource("/Icons/recycle_empty.png"));
        Image image = recycleIcon.getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        ImageIcon scaledRecycleIcon = new ImageIcon(image);

        //recycleButton
        JButton recycleButton = new JButton(scaledRecycleIcon);

        recycleButton.setPreferredSize(new Dimension(32, 32));
        recycleButton.setMinimumSize(new Dimension(32, 32));
        recycleButton.setMaximumSize(new Dimension(32, 32));

        //Make Button invisible
        recycleButton.setContentAreaFilled(false);  // No background
        recycleButton.setBorderPainted(false);      // No border
        recycleButton.setFocusPainted(false);       // No focus outline
        recycleButton.setOpaque(false);             // Transparent

        recycleButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(recycleButton);
    }
}
