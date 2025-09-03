package app.view.flashcardPanel;

import app.Style;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RemoveFlashcardPanel extends JPanel {
    private FlashcardHolderPanel flashcardHolderPanel;
    private JPanel cardContainer;
    private JPanel titleBar;
    private JButton closeButton;
    private JPanel CenterPanel;
    private JButton confirmButton;
    private JButton denyButton;

    public RemoveFlashcardPanel(FlashcardHolderPanel flashcardHolderPanel) {
        this.flashcardHolderPanel = flashcardHolderPanel;

        setLayout();
        setupButtons();
    }

    private void setupButtons(){
        closeButton.addActionListener(e -> {
            flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
        });

        confirmButton.addActionListener(e -> {
            flashcardHolderPanel.removeFlashcard();
            flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.REMOVE_CONFIRM);
            new javax.swing.Timer(900, evt -> {
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
                ((Timer) evt.getSource()).stop();
            }).start();
        });

        denyButton.addActionListener(e -> {
            flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
        });
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

        // Delete confirmation label above buttons
        JLabel deleteConfirmationLabel = new JLabel("Are you sure you want to delete this card?");
        deleteConfirmationLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        deleteConfirmationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteConfirmationLabel.setForeground(Color.black);

        CenterPanel.add(Box.createVerticalGlue());
        CenterPanel.add(deleteConfirmationLabel);
        CenterPanel.add(Box.createRigidArea(new Dimension(0, 75)));

        // button holder panel to house the buttons
        JPanel buttonHolderPanel = new JPanel();
        buttonHolderPanel.setLayout(new BoxLayout(buttonHolderPanel, BoxLayout.X_AXIS));
        buttonHolderPanel.setOpaque(false);

        confirmButton = createCustomButton("Yes");
        denyButton = createCustomButton("No");

        buttonHolderPanel.add(Box.createHorizontalGlue());
        buttonHolderPanel.add(confirmButton);
        buttonHolderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonHolderPanel.add(denyButton);
        buttonHolderPanel.add(Box.createHorizontalGlue());

        CenterPanel.add(buttonHolderPanel);
        CenterPanel.add(Box.createVerticalGlue());

        cardContainer.add(CenterPanel, BorderLayout.CENTER);

    }

    public JButton createCustomButton(String name){
        JButton customButton = new JButton(name);
        customButton.setFocusPainted(false);
        customButton.setBorderPainted(true);
        customButton.setBorder(new CompoundBorder(
                new LineBorder(Style.OUTLINE_COLOR, 2, false),
                new EmptyBorder(10, 20, 10, 20)
        ));
        customButton.setForeground(Color.BLACK);
        customButton.setFont(new Font("Arial", Font.PLAIN, 20));
        customButton.setBackground(Style.ACTIONBUTTON_COLOR);

        customButton.setPreferredSize(new Dimension(150, 75));
        customButton.setMinimumSize(new Dimension(150, 75));
        customButton.setMaximumSize(new Dimension(150, 75));

        return customButton;
    }
}