import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.File;


public class SidebarPanel extends JPanel {
    private JPanel folderListPanel;
    private boolean folderCreationCooldown;
    private JButton folderButton;

    public SidebarPanel() {

        folderCreationCooldown = false;

        setLayout();

        initialFolderSetUp();
    }

    public void setLayout(){
        //SidebarPanel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(250, 0));
        setBackground(Style.SIDEBARPANEL_COLOR);
        setBorder(new MatteBorder(0, 0, 0, 0, Style.OUTLINE_COLOR));

        //Logo on the top
        int logoSize = 150;
        Image logoImage = (new ImageIcon(getClass().getResource("/Images/logo.png"))).getImage();
        logoImage= logoImage.getScaledInstance(logoSize, logoSize, Image.SCALE_FAST);
        JLabel logo = new JLabel(new ImageIcon(logoImage), SwingConstants.CENTER);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            logo.setPreferredSize(new Dimension(logoSize, logoSize));
            logo.setMaximumSize(new Dimension(logoSize, logoSize));
        logo.setMinimumSize(new Dimension(logoSize, logoSize));
        logo.setBorder(new EmptyBorder(30, 0, 30, 0));
        add(logo);

        //folderIcon
        ImageIcon folderIcon = new ImageIcon(getClass().getResource("/Icons/folder_blank.png"));
        JLabel folderIconLabel = new JLabel(folderIcon);

        JLabel folderTextLabel = new JLabel("New Folder");
        folderTextLabel.setFont(Style.BUTTON_FONT);
        folderTextLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        folderTextLabel.setBackground(Style.ADDFOLDERBUTTON_COLOR);
        folderTextLabel.setForeground(Color.BLACK);
        folderTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //folderButton
        folderButton = new JButton();
        folderButton.setFocusPainted(false);
        folderButton.setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 20, 10, 20)
        ));
        folderButton.setBackground(Style.ADDFOLDERBUTTON_COLOR);
        folderButton.setMaximumSize(new Dimension(230, 60));
        folderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        folderButton.setLayout(new BoxLayout(folderButton, BoxLayout.X_AXIS));

        folderButton.add(folderIconLabel);
        folderButton.add(Box.createRigidArea(new Dimension(20, 0)));
        folderButton.add(folderTextLabel);

        add(folderButton);

        //When add folder button pressed add folder with cooldown
        folderButton.addActionListener(e -> {
            if (folderCreationCooldown) return;
            folderCreationCooldown = true;

            createFolder("");

            new javax.swing.Timer(500, evt -> {
                folderCreationCooldown = false;
            }).start();
        });

        //space in between
        add(new Box.Filler(new Dimension(0, 10),new Dimension(0, 25), new Dimension(0, 50)));

        //folderList
        folderListPanel = new JPanel();
        folderListPanel.setLayout(new BoxLayout(folderListPanel, BoxLayout.Y_AXIS));
        folderListPanel.setBackground(Style.SIDEBARPANEL_COLOR);

        //scrollPane
        JScrollPane scrollPane = new JScrollPane(folderListPanel);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Style.SIDEBARPANEL_COLOR);
        add(scrollPane);
    }

    public void createFolder(String name){
        //add Folder to List
        folderListPanel.add(new FolderPanel(name));
        folderListPanel.revalidate();
        folderListPanel.repaint();
    }

    public void initialFolderSetUp(){
        //Array with every .json file in folder
        File folder = new File("./src/FlashCardStorage");
        File[] jsonFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        //If there is a json File then go through every entry in json Files and create Folder for every entry
        if (jsonFiles != null) {
            for (File jsonFile : jsonFiles) {
                String fileName = jsonFile.getName();
                if(!fileName.equals("Default.json")){
                    //Delete empty files
                    if(fileName.equals(".json")){
                        new File("./src/FlashcardStorage/"+fileName).delete();
                    }
                    else{
                        String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
                        createFolder(nameWithoutExtension);
                    }
                }
            }
        }
    }
}
