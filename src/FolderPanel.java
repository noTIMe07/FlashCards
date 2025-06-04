import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
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
    Gson gson;

    public FolderPanel(String name_) {
        name = name_;

        setUp();

        folderButton.addActionListener(e -> {
            Globals.setCurrentFolderPath(filename);
        });

    }

    private void setUp(){
        flashcards = new ArrayList<>();
        filename = "./src/FlashcardStorage/"+name.replaceAll("\\s","")+".json";
        gson = new Gson();
        createFile();

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

        if(name.isEmpty()){
            JTextField nameField = new JTextField("New Folder");
            nameField.setFont(Style.FOLDER_FONT);
            nameField.setOpaque(false);
            nameField.setBorder(null);
            nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
            nameField.setHorizontalAlignment(JTextField.CENTER);
            Globals.setCurrentFolderPath(filename);
            buttonWrapper.add(nameField);
            SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());

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

            nameField.addActionListener(e -> {
                name = nameField.getText().trim();

                if (!name.matches("[a-zA-Z0-9 ]*")||name.isEmpty()||(new File("./src/FlashcardStorage/"+name+".json").exists())){
                    return;
                }

                new File(filename).renameTo(new File("./src/FlashcardStorage/"+name+".json"));
                filename="./src/FlashcardStorage/"+name+".json";
                Globals.setCurrentFolderPath(filename);

                // Replace text field with label
                int index = buttonWrapper.getComponentZOrder(nameField);
                buttonWrapper.remove(nameField);
                folderButton.setText(name);
                buttonWrapper.add(folderButton, index);
                buttonWrapper.revalidate();
                buttonWrapper.repaint();
            });
        }
        else{
            buttonWrapper.add(folderButton, BorderLayout.CENTER);
            buttonWrapper.revalidate();
            buttonWrapper.repaint();
        }
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
            return;
        }
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(flashcards, writer);;
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
