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
    private Timer backgroundScrollTimer;
    private boolean isAnimatingBackground;
    private JPanel outerPanel;
    private JPanel flashcardHolderPanel;
    private CardLayout cardLayout;
    private FlashcardPanelType currentFlashcardType;
    AnimatedSprite cat;

    public FlashcardHolderPanel(AnimatedSprite cat) {
        currentFlashcardType = FLASHCARD;
        this.cat = cat;

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
            if (!card.getLearned()) {
                flashcardPanel.updateFlashcard(card.getFront(), card.getBack());
                currentFlashcard = card;
                setFlashcardVisibility(true);
                return;
            }
        }
        setFlashcardVisibility(false);
    }

    public void playBackgroundScrollAnimation(int durationMillis, Runnable onComplete) {
        if (isAnimatingBackground) return;

        isAnimatingBackground = true;
        backgroundOffsetX = 0;

        int panelWidth = getWidth();
        long startTime = System.currentTimeMillis();

        backgroundScrollTimer = new Timer(16, null);
        backgroundScrollTimer.addActionListener(e -> {

            long elapsed = System.currentTimeMillis() - startTime;
            double t = Math.min(1.0, (double) elapsed / durationMillis);

            // Easing function: ease-in-out
            double easedProgress = easeInOutQuart(t);

            if (easedProgress >= 1) {
                backgroundScrollTimer.stop();
                backgroundOffsetX = panelWidth;
                isAnimatingBackground = false;

                if (onComplete != null) onComplete.run();
            } else {
                backgroundOffsetX = (int) (easedProgress * panelWidth);
                cat.setBackgroundOffSetX((int) (easedProgress*panelWidth));
            }
            repaint();
        });

        backgroundScrollTimer.start();
    }

    private double easeInOutQuart(double x) {
        return x < 0.5
                ? 8 * Math.pow(x, 4)
                : 1 - Math.pow(-2 * x + 2, 4) / 2;
    }

    public boolean isDeckLearned() {
        for (Flashcard card : flashcards) {
            if (!card.getLearned()) {
                return false;
            }
        }
        return true;
    }

    public void addFlashcard(String front, String back) {
        flashcards.add(new Flashcard(front, back, false));
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
        setLayout(new BorderLayout());
        fileName = FolderController.getCurrentFolderPath();
        flashcardPanel = new FlashcardPanel("Start", "Back", this);
        addFlashcardPanel = new AddFlashcardPanel(this);
        removeFlashcardPanel = new RemoveFlashcardPanel(this);
        removeConfirmationFlashcardPanel = new RemoveConfirmationFlashcardPanel();
        editFlashcardPanel = new EditFlashcardPanel(this);
        isAnimatingBackground = false;

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

    public void setFlashcardContent(String front, String back) {
        if (currentFlashcard == null) return;

        currentFlashcard.setFront(front);
        currentFlashcard.setBack(back);

        saveFile();
    }

    public void setFlashcardVisibility(boolean visibility) {
        if (!flashcardHolderPanel.isVisible() && visibility) {
            playBackgroundScrollAnimation(1000, () -> {
                flashcardHolderPanel.setVisible(true);
            });
        } else flashcardHolderPanel.setVisible(visibility);
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

    public FlashcardPanelType getFlashcardPanelType() {
        return currentFlashcardType;
    }

    public Flashcard getCurrentFlashcard() {
        return currentFlashcard;
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if (backgroundImage != null) {
//            g.drawImage(backgroundImage, -backgroundOffsetX, 0, getWidth() * 2, getHeight(), this);
//        }
//    }
}