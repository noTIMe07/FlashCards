package app.view.flashcardPanel;

import app.Style;

import javax.swing.*;
import java.awt.*;

public class CatInventoryPanel extends JPanel {
    private FlashcardHolderPanel flashcardHolderPanel;
    private JPanel cardContainer;
    private JPanel titleBar;
    private JButton closeButton;
    private JPanel CenterPanel;

    public CatInventoryPanel(FlashcardHolderPanel flashcardHolderPanel) {
        this.flashcardHolderPanel = flashcardHolderPanel;

        setLayout();
        setupButtons();
    }

    private void setupButtons(){
        closeButton.addActionListener(e -> {
            flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
            flashcardHolderPanel.setFlashcardVisibility(false);
        });
    }

    private void setLayout(){
        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Create card container
        cardContainer = new JPanel();
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setLayout(new BorderLayout());
        cardContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true)
        ));
        add(cardContainer);

        // Custom title bar
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Style.CONFIRM_TOP);
        titleBar.setPreferredSize(new Dimension(400, 40));

        // Close Button to close Panel
        closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Style.CONFIRM_TOP);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 25));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(55, 40));

        // Add title bar components
        titleBar.add(closeButton, BorderLayout.EAST);
        cardContainer.add(titleBar, BorderLayout.NORTH);

        // Center panel to hold the text panes
        CenterPanel = new JPanel();
        CenterPanel.setLayout(new BoxLayout(CenterPanel, BoxLayout.Y_AXIS));
        CenterPanel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        cardContainer.add(CenterPanel, BorderLayout.CENTER);
    }
}