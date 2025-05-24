import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    private JPanel folderListPanel;

    public SidebarPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(280, 0));
        setBackground(Style.BACKGROUND_COLOR);
        setBorder(new MatteBorder(0, 0, 0, 4, Style.OUTLINE_COLOR));

        JLabel logo = new JLabel("Flashcards", SwingConstants.CENTER);
        logo.setFont(Style.LOGO_FONT);
        logo.setForeground(Style.ACCENT_COLOR);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(new EmptyBorder(30, 0, 20, 0));
        add(logo);

        add(Box.createVerticalGlue());

        JButton addFolderButton = new JButton("Add Folder", UIManager.getIcon("FileView.directoryIcon"));
        addFolderButton.setFont(Style.BUTTON_FONT);
        addFolderButton.setBackground(Style.BUTTON_COLOR);
        addFolderButton.setForeground(Color.BLACK);
        addFolderButton.setFocusPainted(false);
        addFolderButton.setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, true),
                new EmptyBorder(10, 20, 10, 20)
        ));
        addFolderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addFolderButton.setMaximumSize(new Dimension(240, 60));
        add(addFolderButton);

        add(Box.createVerticalGlue());

        folderListPanel = new JPanel();
        folderListPanel.setLayout(new BoxLayout(folderListPanel, BoxLayout.Y_AXIS));
        folderListPanel.setBackground(Style.BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(folderListPanel);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Style.BACKGROUND_COLOR);
        add(scrollPane);

        addFolderButton.addActionListener(e -> {
            folderListPanel.add(new FolderPanel());
            folderListPanel.revalidate();
            folderListPanel.repaint();
        });
    }
}
