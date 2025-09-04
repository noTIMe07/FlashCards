package app.view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import app.Style;
import app.controller.FolderController;
import app.model.Flashcard;
import com.google.gson.Gson;

public class FolderPanel extends JPanel {
    private CenterLayoutLP centerLayoutLP;
    private String name;
    private JButton folderButton;
    private List<Flashcard> flashcards;
    private String filename;
    private JButton recycleButton;
    private JPanel wrapper;
    private JPanel inner;
    private CardLayout cardLayout;
    Gson gson;

    public FolderPanel(String name_, CenterLayoutLP centerLayoutLP) {
        this.centerLayoutLP = centerLayoutLP;
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
        if(Objects.equals(filename, FolderController.getCurrentFolderPath())){
            inner.setBackground(Style.FOLDER_HIGHLIGHTCOLOR);
        }
        else {
            inner.setBackground(Style.FOLDERPANEL_COLOR);
        }
    }

    //Updates folder border if folder is clicked
    public void setFolderBorder(){
        if(Objects.equals(filename, FolderController.getCurrentFolderPath())){
            inner.setBorder(new CompoundBorder(
                    new LineBorder(Style.OUTLINE_COLOR, 2, false),
                    new LineBorder(Color.white, 2, false)
            ));
        }
        else {
            inner.setBorder(new LineBorder(Style.OUTLINE_COLOR, 2, false));
        }

    }

    //Creates file with filename if file doesn't exist yet
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

        FolderController.addPropertyChangeListener(evt -> {
            setFolderColor();
            setFolderBorder();
        });

        //Folder Border
        inner.setBorder(new LineBorder(Style.OUTLINE_COLOR, 2, false));
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

        //If the folder Button is clicked then set to active folder
        //If the folder Button is double-clicked then rename folder
        folderButton.addMouseListener(new MouseAdapter() {
            //click Timer detects double click
            Timer clickTimer;

            @Override
            public void mouseClicked(MouseEvent e) {
                if(centerLayoutLP.isAnimatingBackground()) return;

                if (e.getClickCount() == 2) {
                    //If the clickTimer exists and is running then stop, thus canceling the single click action
                    if (clickTimer != null && clickTimer.isRunning()) clickTimer.stop();
                    renameFolder();
                } else if (e.getClickCount() == 1) {
                    //If the clicked folder is not already active, set active to avoid delay in case of single click
                    if(!Objects.equals(FolderController.getCurrentFolderPath(), filename)){
                        FolderController.setCurrentFolderPath(filename);
                    }
                    else{
                        //The clickTimer sets the delay to detect double-click
                        clickTimer = new Timer(250, ev -> {
                            //After 250ms no double click is detected, then single click action is performed
                            FolderController.setCurrentFolderPath(filename);
                        });
                        clickTimer.setRepeats(false);
                        clickTimer.start();
                    }
                }
            }
        });

        //Button Wrapper to hold name or folder button
        wrapper = new JPanel(cardLayout);
        wrapper.setLayout(new BorderLayout());
        wrapper.setOpaque(false);
        //Wrapper Size
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        wrapper.setPreferredSize(new Dimension(150, 60));
        wrapper.add(folderButton);

        //Manage name field and folder Button
        if(name.isEmpty()){
            addNameField("New Folder");
        }
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
            //If user doesn't confirm, then return
            if (!ConfirmDialog.showConfirmation(recycleButton, name)) {
                return;
            }
            deleteFolder();
        });

        add(inner, BorderLayout.CENTER);
    }

    //Save name, set folder name to name, replace text field with label
    private boolean submitNameField(JTextField nameField){
        //Get name from Name Field
        nameField.setForeground(Color.BLACK);
        String tmpName = nameField.getText().trim();
        //If name includes characters that are not numbers of letters then don't submit
        if (!tmpName.matches("[a-zA-Z0-9 ]*")||tmpName.isEmpty()){
            return false;
        }
        if((new File("./src/FlashcardStorage/"+tmpName+".json").exists()) && !tmpName.equals(name)){
            return false;
        }
        name = tmpName;
        //Rename current folder name to new folder name
        new File(filename).renameTo(new File("./src/FlashcardStorage/"+name+".json"));
        //Update file name and current active folder
        filename="./src/FlashcardStorage/"+name+".json";

        // Replace text field with label
        wrapper.remove(nameField);
        folderButton.setText(name);
        wrapper.add(folderButton);
        wrapper.revalidate();
        wrapper.repaint();

        return true;
    }

    //Add name field to UI
    private void addNameField(String content){
        if(!Objects.equals(FolderController.getCurrentFolderPath(), filename)){
            FolderController.setCurrentFolderPath(filename);
        }
        JTextField nameField = new JTextField(content);
        //Fame Field style
        nameField.setFont(Style.FOLDER_FONT);
        nameField.setOpaque(false);
        nameField.setBorder(null);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.setHorizontalAlignment(JTextField.CENTER);

        //Add Name Field to Wrapper
        wrapper.remove(folderButton);
        wrapper.add(nameField);
        wrapper.revalidate();
        wrapper.repaint();
        //Set focus on Name Field
        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
        //When letter input is not number of letter then turn text red
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = nameField.getText();
                if((new File("./src/FlashcardStorage/"+text+".json").exists()) && !Objects.equals(text, name)) {
                    nameField.setForeground(Color.RED);
                }
                else if (!text.matches("[a-zA-Z0-9 ]*") && !text.isEmpty()) {
                    nameField.setForeground(Color.RED); // Valid input
                } else nameField.setForeground(Color.BLACK);   // Invalid input
            }
        });
        //When focus lost submit file or delete it
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(!submitNameField(nameField)){
                    if(name.isEmpty()) deleteFolder();
                    else{
                        nameField.setText(name);
                        submitNameField(nameField);
                    }
                }
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
            parent.remove(this);

            // Refresh UI
            parent.revalidate();
            parent.repaint();
        }

        //Update mainPanel
        centerLayoutLP.setFlashcardVisibility(false);
    }

    public void renameFolder(){
        addNameField(name);
    }
}
