import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flashcard App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLayout(new BorderLayout());

            // === Retro palette ===
            Color bgColor = new Color(248, 236, 210);     // Light paper
            Color panelColor = new Color(254, 209, 121);  // Yellow-orange
            Color accentColor = new Color(255, 120, 103); // Coral
            Color outlineColor = new Color(50, 50, 50);   // Strong outline
            Color buttonColor = new Color(130, 180, 255); // Sky blue

            Font logoFont = new Font("Comic Sans MS", Font.BOLD, 26);
            Font buttonFont = new Font("Comic Sans MS", Font.PLAIN, 16);
            Font folderFont = new Font("Comic Sans MS", Font.PLAIN, 14);

            // === Sidebar ===
            JPanel sidebar = new JPanel();
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
            sidebar.setPreferredSize(new Dimension(280, 0));
            sidebar.setBackground(bgColor);
            sidebar.setBorder(new MatteBorder(0, 0, 0, 4, outlineColor));

            // === Logo ===
            JLabel logo = new JLabel("Flashcards", SwingConstants.CENTER);
            logo.setFont(logoFont);
            logo.setForeground(accentColor);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            logo.setBorder(new EmptyBorder(30, 0, 20, 0));
            sidebar.add(logo);

            sidebar.add(Box.createVerticalGlue());

            // === Add Folder Button ===
            JButton addFolderButton = new JButton("Add Folder", UIManager.getIcon("FileView.directoryIcon"));
            addFolderButton.setFont(buttonFont);
            addFolderButton.setBackground(buttonColor);
            addFolderButton.setForeground(Color.BLACK);
            addFolderButton.setFocusPainted(false);
            addFolderButton.setBorder(new CompoundBorder(
                    new LineBorder(outlineColor, 2, true),
                    new EmptyBorder(10, 20, 10, 20)
            ));
            addFolderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            addFolderButton.setMaximumSize(new Dimension(240, 60));
            sidebar.add(addFolderButton);

            sidebar.add(Box.createVerticalGlue());

            // === Folder List Scroll Pane ===
            JPanel folderListPanel = new JPanel();
            folderListPanel.setLayout(new BoxLayout(folderListPanel, BoxLayout.Y_AXIS));
            folderListPanel.setBackground(bgColor);

            JScrollPane scrollPane = new JScrollPane(folderListPanel);
            scrollPane.setPreferredSize(new Dimension(300, 300));
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            scrollPane.getViewport().setBackground(bgColor);
            scrollPane.setOpaque(false);
            sidebar.add(scrollPane);

            // === Folder Button Logic ===
            addFolderButton.addActionListener(e -> {
                folderListPanel.add(new Folder(panelColor, outlineColor, folderFont));
                folderListPanel.revalidate();
                folderListPanel.repaint();
            });

            // === Center Panel ===
            JPanel centerPanel = new JPanel();
            centerPanel.setBackground(new Color(232, 220, 200)); // muted beige
            centerPanel.setLayout(new GridBagLayout());
            JLabel centerLabel = new JLabel("Welcome to Flashcards!");
            centerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
            centerLabel.setForeground(accentColor);
            centerPanel.add(centerLabel);

            frame.add(sidebar, BorderLayout.WEST);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}