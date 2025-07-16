package app.view;

import app.Style;
import app.animation.AnimatedSprite;
import app.controller.FolderController;
import app.model.Flashcard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static app.view.FlashcardPanelType.FLASHCARD;

public class FlashcardHolderPanel extends JPanel {
    private FlashcardPanel flashcardPanel;
    private AddFlashcardPanel addFlashcardPanel;
    private RemoveFlashcardPanel removeFlashcardPanel;
    private RemoveConfirmationFlashcardPanel removeConfirmationFlashcardPanel;
    private EditFlashcardPanel editFlashcardPanel;
    private FlashcardActionPanel flashcardActionPanel;
    private static List<Flashcard> flashcards;
    private static String fileName;
    private static Flashcard currentFlashcard;
    private int backgroundOffsetX;
    private JPanel outerPanel;
    private JPanel flashcardHolderPanel;
    private CardLayout cardLayout;
    private FlashcardPanelType currentFlashcardType;
    private CenterLayoutLP centerLayoutLP;

    public FlashcardHolderPanel(CenterLayoutLP centerLayoutLP) {
        currentFlashcardType = FLASHCARD;
        this.centerLayoutLP = centerLayoutLP;

        setUp();
        loadFile();
        study();
    }

    public void study() {
        if (!Objects.equals(fileName, FolderController.getCurrentFolderPath())) {
            fileName = FolderController.getCurrentFolderPath();
            loadFile();
        }
        for (Flashcard card : flashcards) {
            int counter = card.getLearnedCounter();
            LocalDate dueDate = (card.getDateStudied()).plusDays(counter * counter);
            if (!dueDate.isAfter(LocalDate.now())) {
                flashcardPanel.updateFlashcard(card.getFront(), card.getBack());
                currentFlashcard = card;
                setFlashcardVisibility(true);
                return;
            }
        }
        setFlashcardVisibility(false);
    }

    public boolean isDeckLearned() {
        for (Flashcard card : flashcards) {
            // If one card is not learned yet, then return false
            if (!card.isLearned()) {
                return false;
            }
        }
        // If every card is learned, return true
        return true;
    }


    public void setLearned(boolean learned) {
        // If the back panel is not showing, then return
        if (flashcardPanel.isFront() || !flashcardPanel.isVisible()) return;
        // If flashcard is not null, then set it learned
        if (currentFlashcard != null) {
            // If learned = true then add 1 to learned Counter else reset
            if(learned) currentFlashcard.setLearnedCounter(currentFlashcard.getLearnedCounter() + 1);
            else currentFlashcard.setLearnedCounter(0);
            currentFlashcard.setDateStudied(LocalDate.now().toString());
            flashcards.remove(currentFlashcard);
            flashcards.add(currentFlashcard);
            saveFile();
        }
        currentFlashcard = null;
        study();
    }

    public void addFlashcard(String front, String back){
        flashcards.add(new Flashcard(front, back));
        saveFile();
        study();
    }

    public void removeFlashcard() {
        flashcards.remove(currentFlashcard);
        saveFile();
        study();
    }

    public void editFlashcard(String front, String back){
        if(currentFlashcard==null) return;

        currentFlashcard.setFront(front);
        currentFlashcard.setBack(back);

        saveFile();
    }

    private void loadFile() {
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type flashcardListType = new TypeToken<List<Flashcard>>() {
            }.getType();
            flashcards = gson.fromJson(reader, flashcardListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(flashcards, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUp() {
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        fileName = FolderController.getCurrentFolderPath();
        flashcardPanel = new FlashcardPanel("Start", "Back", this);
        addFlashcardPanel = new AddFlashcardPanel(this);
        removeFlashcardPanel = new RemoveFlashcardPanel(this);
        removeConfirmationFlashcardPanel = new RemoveConfirmationFlashcardPanel();
        editFlashcardPanel = new EditFlashcardPanel(this);

        flashcardActionPanel = new FlashcardActionPanel(this);

        outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new GridBagLayout());
        outerPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Style.OUTLINE_COLOR));

        cardLayout = new CardLayout();
        flashcardHolderPanel = new JPanel(cardLayout);
        flashcardHolderPanel.setOpaque(false);

        FolderController.addPropertyChangeListener(evt -> {
            study();
            setFlashcardPanelType(FLASHCARD);
        });

        add(flashcardActionPanel, BorderLayout.NORTH);
        flashcardHolderPanel.add(flashcardPanel, "FlashcardPanel");
        flashcardHolderPanel.add(addFlashcardPanel, "AddFlashcardPanel");
        flashcardHolderPanel.add(removeFlashcardPanel, "RemoveFlashcardPanel");
        flashcardHolderPanel.add(removeConfirmationFlashcardPanel, "RemoveConfirmationFlashcardPanel");
        flashcardHolderPanel.add(editFlashcardPanel, "EditFlashcardPanel");
        outerPanel.add(flashcardHolderPanel);
        add(outerPanel, BorderLayout.CENTER);
    }


    public void setFlashcardPanelType(FlashcardPanelType panelType) {
        cardLayout.show(flashcardHolderPanel, panelType.getName());
        currentFlashcardType = panelType;

        if (!"./src/FlashcardStorage/Default.json".equals(FolderController.getCurrentFolderPath())
                && panelType == FlashcardPanelType.ADD) {
            setFlashcardVisibility(true);
        }

        if (panelType == FlashcardPanelType.EDIT) {
            editFlashcardPanel.updateTextPanes();
        }

        if (panelType == FlashcardPanelType.FLASHCARD) {
            flashcardActionPanel.styleButton(0);
        } else if (panelType == FlashcardPanelType.ADD) {
            flashcardActionPanel.styleButton(1);
        } else if (panelType == FlashcardPanelType.REMOVE) {
            flashcardActionPanel.styleButton(2);
        }
    }

    public void flipCard(){
        if(currentFlashcardType == FLASHCARD){ //Not working in cat animation
            flashcardPanel.flipCard();
        }
    }

    public void setFlashcardContent(String front, String back) {
        if (currentFlashcard == null) return;

        currentFlashcard.setFront(front);
        currentFlashcard.setBack(back);

        saveFile();
    }

    public void setFlashcardVisibility(boolean visibility) {
        if (!flashcardHolderPanel.isVisible() && visibility) {
            centerLayoutLP.playBackgroundScrollAnimation(1000, () -> {
                flashcardHolderPanel.setVisible(true);
            });
        } else flashcardHolderPanel.setVisible(visibility);
    }


    public FlashcardPanelType getFlashcardPanelType() {
        return currentFlashcardType;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }
}