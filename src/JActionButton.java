import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class JActionButton extends JButton {
    public JActionButton(String name, String filepath) {
        setBackground(Style.ACTIONBUTTON_COLOR);
        setFocusPainted(false);
        setBorderPainted(true);
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 20, 10, 20)
        ));
        setForeground(Color.BLACK);

        setPreferredSize(new Dimension(230, 60));
        setMinimumSize(new Dimension(230, 60));
        setMaximumSize(new Dimension(230, 60));

        setLayout(new BorderLayout());

        //Icon
        ImageIcon icon = new ImageIcon(getClass().getResource(filepath));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(32, 32));
        iconLabel.setMinimumSize(new Dimension(32, 32));
        iconLabel.setMaximumSize(new Dimension(32, 32));
        add(iconLabel, BorderLayout.EAST);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(Style.BUTTON_FONT);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(nameLabel, BorderLayout.CENTER);
    }
}
