import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import com.google.gson.Gson;

public class FolderPanel extends JPanel {

    private String name;
    private JButton folderButton;
    private List<Flashcard> flashcards;
    private String filename;
    private JButton recycleButton;
    private JPanel wrapper;
    private JPanel inner;
    Gson gson;

    public FolderPanel(String name_) {
        name = name_;

        setUp();
    }

    //Set variables, createFile(), setLayout()
    private void setUp(){
        //set variables
        flashcards = new ArrayList<>();
        filename = "./src/FlashcardStorage/"+name+".json";
        gson = new Gson();

        createFile();
        setLayout();
    }

    //Updates folder color if folder is clicked
    public void setFolderColor(){
        //if this folder is the current folder, highlight the folder
        if(Objects.equals(filename, Globals.getCurrentFolderPath())){
            inner.setBackground(Style.FOLDER_HIGHLIGHTCOLOR);
        }
        else {
            inner.setBackground(Style.FOLDERPANEL_COLOR);
        }
    }

    //Creates file with filename if file doesn't exist yet
    public void createFile(){
        //creates a new file if the file doesn't exist yet
        if(new File(filename).isFile()){
            return;
        }
        System.out.println(filename);
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(flashcards, writer);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Add folder button, wrapper, and recycle button to layout
    public void setLayout(){
        setLayout(new BorderLayout());
        setMaximumSize(new Dimension(200, 60));
        setBackground(Style.SIDEBARPANEL_COLOR);
        //Create invisible gap
        setBorder(new EmptyBorder(2, 0, 2, 0));

        inner = new JPanel();

        //Inner Panel Layout
        inner.setLayout(new BoxLayout(inner, BoxLayout.X_AXIS));
        //Folder color
        setFolderColor();
        Globals.addPropertyChangeListener(evt -> {
            setFolderColor();
        });
        //Folder Border
        inner.setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(0, 0, 0, 0)
        ));
        inner.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Folder name on Folder Button
        folderButton = new JButton(name);
        folderButton.setFont(Style.FOLDER_FONT);
        folderButton.setForeground(Color.DARK_GRAY);
        folderButton.setBorderPainted(false);
        folderButton.setFocusPainted(false);
        folderButton.setContentAreaFilled(false);
        folderButton.setOpaque(false);
        folderButton.setBorder(null);

        folderButton.setPreferredSize(new Dimension(150, 60));
        folderButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        folderButton.setMinimumSize(new Dimension(50, 60));
        folderButton.setAlignmentY(Component.CENTER_ALIGNMENT);

        //If folder Button clicked then set active folder
        folderButton.addActionListener(e -> {
            Globals.setCurrentFolderPath(filename);
        });

        //Button Wrapper to hold name or folder button
        wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);

        //Manage name field and folder Button
        if(name.isEmpty()){
            addNameField();
        }
        else{
            wrapper.add(folderButton, BorderLayout.CENTER);
            wrapper.revalidate();
            wrapper.repaint();
        }
        //Wrapper Size
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        wrapper.setPreferredSize(new Dimension(150, 60));
        inner.add(wrapper);

        //Space in the middle
        inner.add(Box.createHorizontalGlue());

        //Recycle Bin Icon
        ImageIcon recycleIcon = new ImageIcon(getClass().getResource("/Icons/recycle_empty.png"));

        //recycleButton size
        recycleButton = new JButton(recycleIcon);
        recycleButton.setPreferredSize(new Dimension(32, 32));
        recycleButton.setMinimumSize(new Dimension(32, 32));
        recycleButton.setMaximumSize(new Dimension(32, 32));

        //Make Button invisible
        recycleButton.setContentAreaFilled(false);
        recycleButton.setBorderPainted(false);
        recycleButton.setFocusPainted(false);
        recycleButton.setOpaque(false);

        //Add recycle Button
        recycleButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        inner.add(recycleButton);

        recycleButton.addActionListener(e -> {
            //If user doesn't  confirm, then return
            if (!ConfirmDialog.showConfirmation(recycleButton, name)) {
                return;
            }
            System.out.println("Test");
            deleteFolder();
        });

        add(inner, BorderLayout.CENTER);
    }

    //Save name, set folder name to name, replace text field with label
    private boolean submitNameField(JTextField nameField){
        //Get name from Name Field
        name = nameField.getText().trim();
        //If name includes characters that are not numbers of letters then don't submit
        if (!name.matches("[a-zA-Z0-9 ]*")||name.isEmpty()||(new File("./src/FlashcardStorage/"+name+".json").exists())){
            return false;
        }
        //Rename current folder name to new folder name
        new File(filename).renameTo(new File("./src/FlashcardStorage/"+name+".json"));
        //Update file name and current active folder
        filename="./src/FlashcardStorage/"+name+".json";
        System.out.println(filename);
        Globals.setCurrentFolderPath(filename);

        // Replace text field with label
        int index = wrapper.getComponentZOrder(nameField);
        wrapper.remove(nameField);
        folderButton.setText(name);
        wrapper.add(folderButton, index);
        wrapper.revalidate();
        wrapper.repaint();

        return true;
    }

    //Add name field to UI
    private void addNameField(){
        JTextField nameField = new JTextField("New Folder");
        //Fame Field style
        nameField.setFont(Style.FOLDER_FONT);
        nameField.setOpaque(false);
        nameField.setBorder(null);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        //Update current folder to this folder
        Globals.setCurrentFolderPath(filename);
        //Add Name Field to Wrapper
        wrapper.add(nameField);
        //Set focus on Name Field
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
        //When letter input is not number of letter then turn text red
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = nameField.getText();
                if (text.matches("[a-zA-Z0-9 ]*")) {
                    nameField.setForeground(Color.BLACK); // Valid input
                } else {
                    nameField.setForeground(Color.RED);   // Invalid input
                }
            }
        });
        //When focus lost submit file or delete it
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(!submitNameField(nameField)) deleteFolder();
            }
        });
        //When enter pressed then submit Name Field
        nameField.addActionListener(e -> {
            //transfer Focus so focus lost gets triggered
            nameField.transferFocus();
        });
    }

    public void deleteFolder(){
        //Check if file exists, then delete
        if (new File(filename).exists()) new File(filename).delete();

        //If parent exist, remove itself from parent
        Container parent = this.getParent();
        if (parent != null) {
            parent.remove(this); // remove self from JPanel

            // Refresh UI
            parent.revalidate();
            parent.repaint();
        }
    }
}
