import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class MainPanel extends JPanel {
    FlashcardPanel flashcardPanel;
    private static List<Flashcard> flashcards;
    private static String fileName;
    private static Flashcard currentFlashcard;


    public MainPanel() {
        setUp();
        loadFile();
        study();
        add(flashcardPanel);
    }

    public void study(){
        if(!Objects.equals(fileName, Globals.getCurrentFolderPath())){
            fileName=Globals.getCurrentFolderPath();
            loadFile();
        }
        for(Flashcard card : flashcards){
            if(!card.getLearned()) {
               flashcardPanel.updateFlashcard(card.getFront(), card.getBack());
                currentFlashcard = card;
                flashcardPanel.setVisible(true);
                return;
            }
        }
        flashcardPanel.setVisible(false);
    }

    public void setLearned(boolean learned) {
        if(currentFlashcard!=null){
            currentFlashcard.setLearned(learned);

            //move the card to back
            flashcards.remove(currentFlashcard);
            flashcards.add(currentFlashcard);

            saveFile();
        }

        currentFlashcard = null;
        study();
    }

    private void loadFile(){
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();

            Type flashcardListType = new TypeToken<List<Flashcard>>() {}.getType();
            flashcards = gson.fromJson(reader, flashcardListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Pretty JSON output

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(flashcards, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUp(){
        setBackground(new Color(232, 220, 200));
        setLayout(new GridBagLayout());

        fileName = Globals.getCurrentFolderPath();
        flashcardPanel = new FlashcardPanel("Start", "Back", this);

        Globals.addPropertyChangeListener(evt -> {
            study();
        });
    }

}