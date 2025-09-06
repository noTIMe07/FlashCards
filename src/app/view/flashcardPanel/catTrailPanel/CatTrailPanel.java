package app.view.flashcardPanel.catTrailPanel;

import app.Style;
import app.view.flashcardPanel.FlashcardHolderPanel;
import app.view.flashcardPanel.FlashcardPanelType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CatTrailPanel extends JPanel {
    private FlashcardHolderPanel flashcardHolderPanel;
    private JPanel cardContainer;
    private JPanel titleBar;
    private JButton closeButton;
    private JPanel centerPanel;

    // Progress
    private int flashcardsStudied;
    private int maxMilestone;
    private String catName;

    private TrailPanel trailPanel;
    private List<Milestone> milestones;

    public CatTrailPanel(FlashcardHolderPanel flashcardHolderPanel) {
        this.flashcardHolderPanel = flashcardHolderPanel;
        flashcardsStudied = 0;
        maxMilestone = 10000;
        catName = "Kade";

        setupLayout();
        setupButtons();
        setupMilestones();
    }

    private void setupButtons() {
        closeButton.addActionListener(e -> {
            flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
            flashcardHolderPanel.setFlashcardVisibility(false);
        });
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Container
        cardContainer = new JPanel(new BorderLayout());
        cardContainer.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        cardContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true)
        ));
        add(cardContainer);

        // Title bar
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Style.CONFIRM_TOP);
        titleBar.setPreferredSize(new Dimension(400, 40));

        closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Style.CONFIRM_TOP);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 25));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(55, 40));

        titleBar.add(closeButton, BorderLayout.EAST);
        cardContainer.add(titleBar, BorderLayout.NORTH);

        // Center
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        //Trail
        trailPanel = new TrailPanel(10000, 16, 100, 1000);

        centerPanel.add(trailPanel);
        cardContainer.add(centerPanel, BorderLayout.CENTER);
    }

    private void setupMilestones() {
        milestones = new ArrayList<>();
        milestones.add(new Milestone(10, "🎁"));
        milestones.add(new Milestone(25, "🐾"));
        milestones.add(new Milestone(50, "🎉"));
        milestones.add(new Milestone(75, "🌟"));
        milestones.add(new Milestone(100, "🐱"));
        maxMilestone = milestones.get(milestones.size() - 1).flashcardsRequired;
    }


    // Call this whenever progress changes
    public void updateProgress(int studied) {
        this.flashcardsStudied = studied;
    }

    private static class Milestone {
        int flashcardsRequired;
        String icon;

        Milestone(int flashcardsRequired, String icon) {
            this.flashcardsRequired = flashcardsRequired;
            this.icon = icon;
        }
    }
}
