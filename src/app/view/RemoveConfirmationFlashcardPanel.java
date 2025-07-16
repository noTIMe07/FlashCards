package app.view;

import app.Style;

import javax.swing.*;
import java.awt.*;

public class RemoveConfirmationFlashcardPanel extends JPanel {
    private JPanel cardContainer;

    public RemoveConfirmationFlashcardPanel() {
        setLayout();
    }

    private void setLayout(){
        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Create card container
        cardContainer = new JPanel();
        cardContainer.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        cardContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true)
        ));
        cardContainer.setLayout(new GridBagLayout());

        // Delete confirmation label above buttons
        JLabel deleteConfirmationLabel = new JLabel("The card was deleted successfully");
        deleteConfirmationLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        deleteConfirmationLabel.setForeground(Color.black);
        deleteConfirmationLabel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        deleteConfirmationLabel.setAlignmentX(CENTER_ALIGNMENT);

        cardContainer.add(deleteConfirmationLabel);

        add(cardContainer);
    }
}