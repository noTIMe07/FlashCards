import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
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
    Gson gson;

    public FolderPanel(String name_) {
        name = name_;

        setUp();
        createFile();

        folderButton.addActionListener(e -> {
            Globals.setCurrentFolderPath(filename);
        });

    }

    private void setUp(){
        flashcards = new ArrayList<>();
        filename = "./src/FlashcardStorage/"+name.replaceAll("\\s","")+".json";
        gson = new Gson();

        //Folder Layout and Style
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(200, 60));
        setFolderColor();
        Globals.addPropertyChangeListener(evt -> {
            setFolderColor();
        });
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(0, 0, 0, 0)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        //Folder name
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

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new BorderLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(folderButton, BorderLayout.CENTER);
        buttonWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        buttonWrapper.setPreferredSize(new Dimension(150, 60));
        add(buttonWrapper);

        //Space in the middle
        add(Box.createHorizontalGlue());

        //Recycle Bin Icon
        ImageIcon recycleIcon = new ImageIcon(getClass().getResource("/Icons/recycle_empty.png"));
        Image image = recycleIcon.getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        ImageIcon scaledRecycleIcon = new ImageIcon(image);

        //recycleButton
        recycleButton = new JButton(scaledRecycleIcon);

        recycleButton.setPreferredSize(new Dimension(32, 32));
        recycleButton.setMinimumSize(new Dimension(32, 32));
        recycleButton.setMaximumSize(new Dimension(32, 32));

        //Make Button invisible
        recycleButton.setContentAreaFilled(false);
        recycleButton.setBorderPainted(false);
        recycleButton.setFocusPainted(false);
        recycleButton.setOpaque(false);

        recycleButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        add(recycleButton);
    }

    public void setFolderColor(){
        if(Objects.equals(filename, Globals.getCurrentFolderPath())){
            setBackground(Style.FOLDER_HIGHLIGHTCOLOR);
        }
        else {
            setBackground(Style.FOLDER_COLOR);
        }
    }

    public void createFile(){
        if(new File(filename).isFile()){
            System.out.println("JSON file already exists");
            return;
        }
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(flashcards, writer);
            System.out.println("JSON file created successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addFlashcard(String question, String answer, Boolean leaned){
        flashcards.add(new Flashcard(question, answer, leaned));
    }

    public void removeFlashcard(String question, String answer, Boolean leaned){
        flashcards.remove(new Flashcard(question, answer, leaned));
    }


}
