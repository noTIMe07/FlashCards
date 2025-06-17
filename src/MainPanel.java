import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class MainPanel extends JPanel {
    private FlashcardPanel flashcardPanel;
    private AddFlashcardPanel addFlashcardPanel;
    private RemoveFlashcardPanel removeFlashcardPanel;
    private RemoveConfirmationFlashcardPanel removeConfirmationFlashcardPanel;
    private FlashcardActionPanel flashcardActionPanel;
    private static List<Flashcard> flashcards;
    private static String fileName;
    private static Flashcard currentFlashcard;
    private Image backgroundImage;
    private JPanel outerPanel;
    private JPanel flashcardHolderPanel;
    private CardLayout cardLayout;
    private String currentFlashcardType;

    public MainPanel() {
        currentFlashcardType = "";
        setUp();
        loadFile();
        study();

        add(flashcardActionPanel, BorderLayout.NORTH);
        flashcardHolderPanel.add(flashcardPanel, "FlashcardPanel");
        flashcardHolderPanel.add(addFlashcardPanel, "AddFlashcardPanel");
        flashcardHolderPanel.add(removeFlashcardPanel, "RemoveFlashcardPanel");
        flashcardHolderPanel.add(removeConfirmationFlashcardPanel, "RemoveConfirmationFlashcardPanel");
        outerPanel.add(flashcardHolderPanel);
        add(outerPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void study() {
        if (!Objects.equals(fileName, Globals.getCurrentFolderPath())) {
            fileName = Globals.getCurrentFolderPath();
            loadFile();
        }
        for (Flashcard card : flashcards) {
            if (!card.getLearned()) {
                flashcardPanel.updateFlashcard(card.getFront(), card.getBack());
                currentFlashcard = card;
                flashcardHolderPanel.setVisible(true);
                return;
            }
        }
        flashcardHolderPanel.setVisible(false);
    }

    public boolean isDeckLearned(){
        for (Flashcard card : flashcards) {
            if (!card.getLearned()) {
                return false;
            }
        }
        return true;
    }

    public void setFlashcardVisibility(boolean visibility){
        flashcardHolderPanel.setVisible(visibility);
    }

    public void setLearned(boolean learned) {
        if (currentFlashcard != null) {
            currentFlashcard.setLearned(learned);
            flashcards.remove(currentFlashcard);
            flashcards.add(currentFlashcard);
            saveFile();
        }
        currentFlashcard = null;
        study();
    }

    public void addFlashcard(String front, String back){
        flashcards.add(new Flashcard(front, back, false));
        saveFile();
        study();
    }

    public void removeFlashcard(){
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

    public void saveFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(flashcards, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFlashcardPanelType(String panelType){
        cardLayout.show(flashcardHolderPanel, panelType);
        currentFlashcardType = panelType;

        if (!"./src/FlashcardStorage/Default.json".equals(Globals.getCurrentFolderPath())
                && "AddFlashcardPanel".equals(panelType)) {
            flashcardHolderPanel.setVisible(true);
        }

        //Grey out buttons that are not pressable
        if(currentFlashcardType.equals("FlashcardPanel")) flashcardActionPanel.styleButton(0);
        else if(currentFlashcardType.equals("AddFlashcardPanel")) flashcardActionPanel.styleButton(1);
        else if(currentFlashcardType.equals("RemoveFlashcardPanel")) flashcardActionPanel.styleButton(2);
    }

    public String getFlashcardPanelType(){
        return currentFlashcardType;
    }

    private void setUp() {
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        fileName = Globals.getCurrentFolderPath();
        flashcardPanel = new FlashcardPanel("Start", "Back", this);
        addFlashcardPanel = new AddFlashcardPanel(this);
        removeFlashcardPanel = new RemoveFlashcardPanel(this);
        removeConfirmationFlashcardPanel = new RemoveConfirmationFlashcardPanel();
        backgroundImage = new ImageIcon(getClass().getResource("/Images/background.png")).getImage();

        flashcardActionPanel = new FlashcardActionPanel(this);

        outerPanel = new JPanel();
        outerPanel.setOpaque(false);
        outerPanel.setLayout(new GridBagLayout());
        outerPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Style.OUTLINE_COLOR));

        cardLayout = new CardLayout();
        flashcardHolderPanel = new JPanel(cardLayout);
        flashcardHolderPanel.setOpaque(false);

        Globals.addPropertyChangeListener(evt -> {
            study();
            setFlashcardPanelType("FlashcardPanel");
        });
    }
}
