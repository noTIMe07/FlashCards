package app.view.flashcardPanel;

import app.Style;

import javax.swing.*;
import java.awt.*;

public class EmptyFlashcardPanel extends JPanel {
    private FlashcardHolderPanel flashcardHolderPanel;
    private JPanel cardContainer;
    private JPanel centerPanel;
    private JLabel label;

    public EmptyFlashcardPanel(FlashcardHolderPanel flashcardHolderPanel) {
        this.flashcardHolderPanel = flashcardHolderPanel;

        setLayout();
    }

    private void setLayout(){
        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Create card container
        cardContainer = new JPanel();
        cardContainer.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        cardContainer.setLayout(new BorderLayout());
        cardContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true)
        ));
        add(cardContainer);

        // Center panel to hold the text panes
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        JLabel label = new JLabel("You studied all cards in this deck", SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 24));
        label.setForeground(new Color(60, 40, 40));
        centerPanel.add(label, BorderLayout.CENTER);

        cardContainer.add(centerPanel, BorderLayout.CENTER);
    }
}
