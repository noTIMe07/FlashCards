import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flashcard App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLayout(new BorderLayout());

            // ==== Sidebar panel ====
            JPanel sidebar = new JPanel();
            sidebar.setLayout(new BorderLayout());
            sidebar.setPreferredSize(new Dimension(250, 0));

            // Top: Logo
            JLabel logo = new JLabel("Flashcards", SwingConstants.CENTER);
            logo.setFont(new Font("SansSerif", Font.BOLD, 20));
            logo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
            sidebar.add(logo, BorderLayout.NORTH);

            // Center: Folder list inside a scroll pane
            JPanel folderListPanel = new JPanel();
            folderListPanel.setLayout(new BoxLayout(folderListPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(folderListPanel);
            sidebar.add(scrollPane, BorderLayout.CENTER);

            // Bottom: Add Folder button
            JButton addFolderButton = new JButton("Add Folder");
            addFolderButton.setPreferredSize(new Dimension(250, 40));
            sidebar.add(addFolderButton, BorderLayout.SOUTH);

            // Button logic to add new Folder
            addFolderButton.addActionListener(e -> {
                folderListPanel.add(new Folder());
                folderListPanel.revalidate(); // triggers layout update
                folderListPanel.repaint();    // triggers paint
            });

            // Add sidebar to main frame
            frame.add(sidebar, BorderLayout.WEST);

            // Dummy center panel
            frame.add(new JPanel(), BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
