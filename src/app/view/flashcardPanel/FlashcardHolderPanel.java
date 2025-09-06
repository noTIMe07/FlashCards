package app.view.flashcardPanel;

import app.Style;
import app.controller.FolderController;
import app.model.Flashcard;
import app.view.*;
import app.view.actionPanel.ActionHolderPanel;
import app.view.flashcardPanel.catTrailPanel.CatTrailPanel;
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

import static app.view.flashcardPanel.FlashcardPanelType.*;

public class FlashcardHolderPanel extends JPanel {

    //Panels
    private FlashcardPanel flashcardPanel;
    private EmptyFlashcardPanel emptyFlashcardPanel;
    private AddFlashcardPanel addFlashcardPanel;
    private RemoveFlashcardPanel removeFlashcardPanel;
    private RemoveConfirmationFlashcardPanel removeConfirmationFlashcardPanel;
    private EditFlashcardPanel editFlashcardPanel;
    private CatTrailPanel catTrailPanel;
    private CatInventoryPanel catInventoryPanel;
    private ActionHolderPanel actionHolderPanel;

    // Layout components
    private JPanel outerPanel;
    private JPanel flashcardHolder;
    private CardLayout cardLayout;

    // Flashcard data
    private static List<Flashcard> flashcards;
    private static String fileName;
    private static Flashcard currentFlashcard;
    private FlashcardPanelType currentFlashcardType;

    private final CenterLayoutLP centerLayoutLP;

    public FlashcardHolderPanel(CenterLayoutLP centerLayoutLP) {
        this.currentFlashcardType = FLASHCARD;
        this.centerLayoutLP = centerLayoutLP;

        setUp();
        loadFile();
        study();
    }

    //Selects the next due flashcard or shows empty panel if none are due
    public void study() {
        // Update filename if folder changed
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
                setFlashcardPanelType(FLASHCARD);
                return;
            }
        }
        setFlashcardPanelType(EMPTY);
    }

    //Checks whether the whole deck is learned.
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

    private void loadFile() {
        try (FileReader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type flashcardListType = new TypeToken<List<Flashcard>>() {}.getType();
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
        setLayout(new BorderLayout());

        //Panels
        fileName = FolderController.getCurrentFolderPath();
        flashcardPanel = new FlashcardPanel("Start", "Back", this);
        emptyFlashcardPanel = new EmptyFlashcardPanel(this);
        addFlashcardPanel = new AddFlashcardPanel(this);
        removeFlashcardPanel = new RemoveFlashcardPanel(this);
        removeConfirmationFlashcardPanel = new RemoveConfirmationFlashcardPanel();
        editFlashcardPanel = new EditFlashcardPanel(this);
        catTrailPanel = new CatTrailPanel(this);
        catInventoryPanel = new CatInventoryPanel(this);

        actionHolderPanel = new ActionHolderPanel(this);

        outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Style.OUTLINE_COLOR));

        cardLayout = new CardLayout();
        flashcardHolder = new JPanel(cardLayout);
        flashcardHolder.setOpaque(false);

        //Folder listener
        FolderController.addPropertyChangeListener(evt -> {
            if(FolderController.getCurrentFolderPath()!="./src/FlashcardStorage/Default.json"){
                study();
                setFlashcardVisibility(true);
            }
            else{
                setFlashcardVisibility(false);
            }
        });

        //Assemble layout
        add(actionHolderPanel, BorderLayout.NORTH);
        flashcardHolder.add(flashcardPanel, "FlashcardPanel");
        flashcardHolder.add(emptyFlashcardPanel, "EmptyFlashcardPanel");
        flashcardHolder.add(addFlashcardPanel, "AddFlashcardPanel");
        flashcardHolder.add(removeFlashcardPanel, "RemoveFlashcardPanel");
        flashcardHolder.add(removeConfirmationFlashcardPanel, "RemoveConfirmationFlashcardPanel");
        flashcardHolder.add(editFlashcardPanel, "EditFlashcardPanel");
        flashcardHolder.add(catTrailPanel, "CatTrailPanel");
        flashcardHolder.add(catInventoryPanel, "CatInventoryPanel");
        flashcardHolder.setVisible(false);

        outerPanel.add(flashcardHolder);
        add(outerPanel, BorderLayout.CENTER);
    }

    public void setFlashcardPanelType(FlashcardPanelType panelType) {
        cardLayout.show(flashcardHolder, panelType.getName());
        currentFlashcardType = panelType;

        if (panelType == TRAIL) setFlashcardVisibility(true);
        if (panelType == INVENTORY) setFlashcardVisibility(true);

        switch (panelType) {
            case FLASHCARD:
                actionHolderPanel.styleButton(0);
                break;
            case ADD:
                actionHolderPanel.styleButton(1);
                break;
            case REMOVE:
                actionHolderPanel.styleButton(2);
                break;
            case EDIT:
                actionHolderPanel.styleButton(3);
                editFlashcardPanel.updateTextPanes();
                break;
            case TRAIL:
                actionHolderPanel.styleButton(4);
                break;
            case INVENTORY:
                actionHolderPanel.styleButton(5);
                break;
        }

        if (panelType == TRAIL || panelType == INVENTORY) {
            setFlashcardVisibility(true);
        }
    }

    private void updateActionHolderPanel(){
        boolean isFlashcardRelated =
                currentFlashcardType == FLASHCARD ||
                        currentFlashcardType == ADD ||
                        currentFlashcardType == REMOVE ||
                        currentFlashcardType == EDIT ||
                        currentFlashcardType == EMPTY;

        if (isFlashcardRelated && flashcardHolder.isVisible()) {
            actionHolderPanel.show("FlashcardActionPanel");
        } else {
            actionHolderPanel.show("CatActionPanel");
        }
    }

    public void flipCard(){
        if(currentFlashcardType == FLASHCARD){ //Not working in cat animation
            flashcardPanel.flipCard();
        }
    }

    //Marks the current flashcard as learned or not.
    public void setLearned(boolean learned) {

        // If the back panel is not showing, then return
        if (flashcardPanel.isFront() || !flashcardPanel.isVisible()) return;
        if (currentFlashcard == null) return;

        // If learned = true then add 1 to learned Counter else reset
        if (learned) {
            currentFlashcard.setLearnedCounter(currentFlashcard.getLearnedCounter() + 1);
        } else {
            currentFlashcard.setLearnedCounter(0);
        }

        currentFlashcard.setDateStudied(LocalDate.now().toString());
        flashcards.remove(currentFlashcard);
        flashcards.add(currentFlashcard);

        saveFile();
        currentFlashcard = null;
        study();
    }

    public void setFlashcardContent(String front, String back) {
        if (currentFlashcard == null) return;

        currentFlashcard.setFront(front);
        currentFlashcard.setBack(back);
        saveFile();
    }

    public void setFlashcardVisibility(boolean visibility) {
        //if not visible but supposed to be visible, then play animation in
        if (!flashcardHolder.isVisible() && visibility) {
            centerLayoutLP.playBackgroundScrollAnimationIn(1000, () -> {
                flashcardHolder.setVisible(true);
                updateActionHolderPanel();
            });
        }
        // if visible but not supposed to be visible, then play animation out
        if(flashcardHolder.isVisible() && !visibility) {
            flashcardHolder.setVisible(false);
            updateActionHolderPanel();
            centerLayoutLP.playBackgroundScrollAnimationOut(1000, () -> {});
        }
        updateActionHolderPanel();
    }

    public FlashcardPanelType getFlashcardPanelType() {
        return currentFlashcardType;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }
}