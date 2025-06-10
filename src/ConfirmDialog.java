import javax.swing.*;
import java.awt.*;

public class ConfirmDialog {

    private boolean confirmed = false;

    public ConfirmDialog(Component parent, String fileName) {
        // Create a custom undecorated dialog
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent));
        dialog.setUndecorated(true);
        dialog.setModal(true);
        dialog.setBackground(Style.CONFIRM_MAIN);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Style.CONFIRM_MAIN);
        mainPanel.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 2));

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Style.CONFIRM_TOP);
        titleBar.setPreferredSize(new Dimension(400, 30));

        JLabel title = new JLabel("Confirm Deletion");
        title.setForeground(Color.BLACK);
        title.setFont(Style.BUTTON_FONT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JButton closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Style.CONFIRM_TOP);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFont(Style.BUTTON_FONT);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(45, 30));
        closeButton.addActionListener(e -> dialog.dispose());

        titleBar.add(title, BorderLayout.WEST);
        titleBar.add(closeButton, BorderLayout.EAST);

        mainPanel.add(titleBar, BorderLayout.NORTH);

        // Main content panel
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel message = new JLabel("Do you really want to delete \"" + fileName + "\"?");
        message.setForeground(Color.BLACK);
        message.setFont(Style.BUTTON_FONT);
        message.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(message, BorderLayout.CENTER);

        JButton yesButton = createStyledButton("Yes");
        JButton noButton = createStyledButton("No");

        yesButton.setPreferredSize(new Dimension(100, 40));
        noButton.setPreferredSize(new Dimension(100, 40));

        yesButton.addActionListener(e -> {
            confirmed = true;
            dialog.dispose();
        });

        noButton.addActionListener(e -> {
            confirmed = false;
            dialog.dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(panel, BorderLayout.CENTER);

        dialog.setContentPane(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Style.BUTTON_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    public boolean wasConfirmed() {
        return confirmed;
    }

    // Static helper method
    public static boolean showConfirmation(Component parent, String fileName) {
        ConfirmDialog dialog = new ConfirmDialog(parent, fileName);
        return dialog.wasConfirmed();
    }
}
