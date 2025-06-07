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
    Gson gson;

    public FolderPanel(String name_) {
        name = name_;

        setUp();
    }

    private void setUp(){
        //set variables
        flashcards = new ArrayList<>();
        filename = "./src/FlashcardStorage/"+name.replaceAll("\\s","")+".json";
        gson = new Gson();

        createFile();
        setLayout();
    }

    public void setFolderColor(){
        //if this folder is the current folder, highlight the folder
        if(Objects.equals(filename, Globals.getCurrentFolderPath())){
            setBackground(Style.FOLDER_HIGHLIGHTCOLOR);
        }
        else {
            setBackground(Style.FOLDER_COLOR);
        }
    }

    public void createFile(){
        //creates a new file if the file doesn't exist yet
        if(new File(filename).isFile()){
            return;
        }
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(flashcards, writer);;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLayout(){
        //Folder Panel Layout
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(200, 60));
        //Folder color
        setFolderColor();
        Globals.addPropertyChangeListener(evt -> {
            setFolderColor();
        });
        //Folder Border
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(0, 0, 0, 0)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);

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
        add(wrapper);

        //Space in the middle
        add(Box.createHorizontalGlue());

        //Recycle Bin Icon
        ImageIcon recycleIcon = new ImageIcon(getClass().getResource("/Icons/recycle_empty.png"));
        Image image = recycleIcon.getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        ImageIcon scaledRecycleIcon = new ImageIcon(image);

        //recycleButton size
        recycleButton = new JButton(scaledRecycleIcon);

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
        add(recycleButton);
    }

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
            submitNameField(nameField);
        });
    }

    public void deleteFolder(){

    }

    public void addFlashcard(String question, String answer, Boolean leaned){
        flashcards.add(new Flashcard(question, answer, leaned));
    }

    public void removeFlashcard(String question, String answer, Boolean leaned){
        flashcards.remove(new Flashcard(question, answer, leaned));
    }
}
