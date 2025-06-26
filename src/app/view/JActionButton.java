package app.view;

import app.Style;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class JActionButton extends JButton {
    public JActionButton(String name, String filepath) {
        setBackground(Style.ACTIONBUTTON_COLOR);
        setFocusPainted(false);
        setBorderPainted(true);
        setBorder(new LineBorder(Style.OUTLINE_COLOR, 2, false));
        setForeground(Color.BLACK);

        // Content holder panel to draw additional empty border
        JPanel contentHolderPanel = new JPanel();
        contentHolderPanel.setOpaque(false);
        contentHolderPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        contentHolderPanel.setLayout(new BorderLayout());

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
        contentHolderPanel.add(iconLabel, BorderLayout.EAST);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentHolderPanel.add(nameLabel, BorderLayout.CENTER);

        add(contentHolderPanel);

        // Override paint to skip blue fill when pressed
        setUI(new BasicButtonUI() {
            @Override
            protected void paintButtonPressed(Graphics g, AbstractButton b) {
                // Do nothing
            }
        });
    }

    public void setActionButtonActive(Boolean active){
        if(active){
            setBorder(new CompoundBorder(
                    new LineBorder(Style.OUTLINE_COLOR, 2, false),
                    new LineBorder(Color.white, 2, false)
            ));
            setBackground(Style.FOLDER_HIGHLIGHTCOLOR);
        }
        else {
            setBorder(new LineBorder(Style.OUTLINE_COLOR, 2, false));
            setBackground(Style.ACTIONBUTTON_COLOR);
        }
    }
}
