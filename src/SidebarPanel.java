import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;


public class SidebarPanel extends JPanel {
    private JPanel folderListPanel;

    public SidebarPanel() {

        //SidebarPanel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setBackground(Style.BACKGROUND_COLOR);
        setBorder(new MatteBorder(0, 0, 0, 4, Style.OUTLINE_COLOR));

        //Logo on the top
        JLabel logo = new JLabel("Flashcards", SwingConstants.CENTER);
        logo.setFont(Style.LOGO_FONT);
        logo.setForeground(Style.ACCENT_COLOR);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(logo);

        //folderIcon
        ImageIcon folderIcon = new ImageIcon(getClass().getResource("/Icons/folder_blank.png"));
        Image image = folderIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon scaledFolderIcon = new ImageIcon(image);

        //folderButton
        JButton folderButton = new JButton("New Folder", scaledFolderIcon);
        folderButton.setFont(Style.BUTTON_FONT);
        folderButton.setBackground(Style.BUTTON_COLOR);
        folderButton.setForeground(Color.BLACK);
        folderButton.setFocusPainted(false);
        folderButton.setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 20, 10, 20)
        ));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        folderButton.setMaximumSize(new Dimension(230, 60));
        add(folderButton);

        //space in between
        add(new Box.Filler(new Dimension(0, 10),new Dimension(0, 25), new Dimension(0, 50)));

        //folderList
        folderListPanel = new JPanel();
        folderListPanel.setLayout(new BoxLayout(folderListPanel, BoxLayout.Y_AXIS));
        folderListPanel.setBackground(Style.BACKGROUND_COLOR);

        //scrollPane
        JScrollPane scrollPane = new JScrollPane(folderListPanel);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Style.BACKGROUND_COLOR);
        add(scrollPane);

        initialFolderSetUp();

        folderButton.addActionListener(e -> {
            createFolder("Folder1");
        });
    }

    public void createFolder(String name){
        //add Folder to List
        folderListPanel.add(new FolderPanel(name));

        //add space in between to list
        folderListPanel.add(new Box.Filler(new Dimension(0, 3),new Dimension(0, 5), new Dimension(0, 8)));

        folderListPanel.revalidate();
        folderListPanel.repaint();
    }

    public void initialFolderSetUp(){
        File folder = new File("./src/FlashCardStorage");
        File[] jsonFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (jsonFiles != null) {
            for (File jsonFile : jsonFiles) {
                String fileName = jsonFile.getName();
                String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));

                createFolder(nameWithoutExtension);
            }
        }
    }
}
