import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;
import com.google.gson.Gson;

public class FolderPanel extends JPanel {

    private String name;
    private JLabel nameLabel;
    private List<Flashcard> flashcards;
    private String filename;
    Gson gson;

    public FolderPanel(String name_) {
        name = name_;
        setUpLayout();

        flashcards = new ArrayList<>();
        filename = "./src/FlashcardStorage/"+name.replaceAll("\\s","")+".json";
        gson = new Gson();

        createFile();

    }

    private void setUpLayout(){
        //Folder Layout and Style
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setMaximumSize(new Dimension(200, 60));
        setBackground(Style.FOLDER_COLOR);
        setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 15, 10, 15)
        ));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        //Folder name
        nameLabel = new JLabel(name, JLabel.LEFT);
        nameLabel.setFont(Style.FOLDER_FONT);
        nameLabel.setForeground(Color.DARK_GRAY);
        add(nameLabel);

        //Space in the middle
        add(Box.createHorizontalGlue());

        //Recycle Bin Icon
        ImageIcon recycleIcon = new ImageIcon(getClass().getResource("/Icons/recycle_empty.png"));
        Image image = recycleIcon.getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        ImageIcon scaledRecycleIcon = new ImageIcon(image);

        //recycleButton
        JButton recycleButton = new JButton(scaledRecycleIcon);

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
