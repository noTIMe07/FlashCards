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

public class MainPanel extends JPanel {
    FlashcardPanel flashcardPanel;
    private static List<Flashcard> flashcards;
    private static String fileName;
    private static Flashcard currentFlashcard;


    public MainPanel() {
        setUp();
        loadFile();
        System.out.println(flashcards);
        study();
        add(flashcardPanel);
    }

    public void study(){
        for(Flashcard card : flashcards){
            if(!card.getLearned()) {
               flashcardPanel.updateFlashcard(card.getFront(), card.getBack());
                currentFlashcard = card;
                return;
            }
        }
        flashcardPanel.updateFlashcard("last Card", "back, still need to implement default background");
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

    private static void loadFile(){
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();

            Type flashcardListType = new TypeToken<List<Flashcard>>() {}.getType();
            flashcards = gson.fromJson(reader, flashcardListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(){
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

        fileName = "./src/FlashcardStorage/CS.json";
        flashcardPanel = new FlashcardPanel("Start", "Back", this);

    }

}