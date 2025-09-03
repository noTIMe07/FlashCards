package app.view.flashcardPanel;

import app.Style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FlashcardPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardContainer;
    private static JLabel questionLabel;
    private static JLabel answerLabel;
    private JButton centerButton;
    private JButton flipButton;
    private JButton reverseButton;
    private JButton wrongButton;
    private JButton rightButton;
    private FlashcardHolderPanel flashcardHolderPanel;

    private boolean isFront;

    public FlashcardPanel(String question, String answer, FlashcardHolderPanel flashcardHolderPanel) {
        this.flashcardHolderPanel = flashcardHolderPanel;

        isFront = true;

        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        //setPreferredSize(new Dimension(1000, 700));

        // Create card layout for flashcard front/back
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        cardContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Front (question)
        questionLabel = createCardLabel(question);

        // Center button so user can click anywhere on card to flip
        centerButton = new JButton();
        centerButton.setOpaque(false);
        centerButton.setFocusPainted(false);
        centerButton.setContentAreaFilled(false);
        centerButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        centerButton.setLayout(new BorderLayout());
        centerButton.add(questionLabel);

        JPanel frontCard = new JPanel(new BorderLayout());
        frontCard.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        frontCard.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true));
        frontCard.add(centerButton, BorderLayout.CENTER);

        JPanel frontButtonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        frontButtonHolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 10,0));
        frontButtonHolder.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Front Flip Button
        flipButton = new JButton("Flip");
        flipButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        flipButton.setBackground(new Color(255, 149, 128));
        flipButton.setForeground(Color.WHITE);
        flipButton.setFocusPainted(false);
        //flipButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        flipButton.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 3, true));
        flipButton.setPreferredSize(new Dimension(672, 60));
        flipButton.setMinimumSize(new Dimension(672, 60));
        flipButton.setMaximumSize(new Dimension(672, 60));
        frontButtonHolder.add(flipButton);
        frontCard.add(frontButtonHolder, BorderLayout.SOUTH);

        // Back (answer)
        answerLabel = createCardLabel(answer);
        JPanel backCard = new JPanel(new BorderLayout());
        backCard.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        backCard.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true));
        backCard.add(answerLabel, BorderLayout.CENTER);

        //Back Card buttons
        JPanel backButtonHolder = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        backButtonHolder.setBorder(BorderFactory.createEmptyBorder(0, 0, 10,0));
        backButtonHolder.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        JPanel backCardButtons = new JPanel(new BorderLayout());
        backCardButtons.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 3, true));

        //reverseButton
        reverseButton = new JButton("←");
        reverseButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        reverseButton.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        reverseButton.setFocusPainted(false);
        reverseButton.setBorder(BorderFactory.createEmptyBorder(3, 25, 3, 25));
        backCardButtons.add(reverseButton, BorderLayout.WEST);

        //answer Buttons
        JPanel answerButtons = new JPanel(new GridLayout(0, 2));
        answerButtons.setBackground(new Color(104, 166, 145));

        //wrongButton
        wrongButton = new JButton("Wrong");
        wrongButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        wrongButton.setBackground(new Color(255, 149, 128));
        wrongButton.setForeground(Color.WHITE);
        wrongButton.setFocusPainted(false);
        wrongButton.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 3));
        wrongButton.setPreferredSize(new Dimension(300, 54));
        wrongButton.setMinimumSize(new Dimension(300, 54));
        wrongButton.setMaximumSize(new Dimension(300, 54));
        answerButtons.add(wrongButton);

        //rightButton
        rightButton = new JButton("Right");
        rightButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        rightButton.setBackground(new Color(104, 166, 145));
        rightButton.setForeground(Color.WHITE);
        rightButton.setFocusPainted(false);
        rightButton.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        rightButton.setPreferredSize(new Dimension(300, 54));
        rightButton.setMinimumSize(new Dimension(300, 54));
        rightButton.setMaximumSize(new Dimension(300, 54));
        answerButtons.add(rightButton);

        //adding to Buttons and Cards to layout
        backCardButtons.add(answerButtons, BorderLayout.CENTER);
        backButtonHolder.add(backCardButtons);
        backCard.add(backButtonHolder, BorderLayout.SOUTH);
        cardContainer.add(frontCard, "question");
        cardContainer.add(backCard, "answer");
        add(cardContainer, BorderLayout.CENTER);

        //Button functionality
        centerButton.addActionListener(e -> {
            isFront = !isFront;
            cardLayout.next(cardContainer);
        });
        flipButton.addActionListener(e -> {
            isFront = !isFront;
            cardLayout.next(cardContainer);
        });
        reverseButton.addActionListener(e -> {
            isFront = !isFront;
            cardLayout.next(cardContainer);
        });

        rightButton.addActionListener(e -> flashcardHolderPanel.setLearned(true));
        wrongButton.addActionListener(e -> flashcardHolderPanel.setLearned(false));
    }

    private JLabel createCardLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.PLAIN, 24));
        label.setForeground(new Color(60, 40, 40));
        return label;
    }

    public void updateFlashcard(String question, String answer){
        questionLabel.setText(question);
        answerLabel.setText(answer);
        isFront = true;
        cardLayout.first(cardContainer);
    }

    public void flipCard(){
        isFront = !isFront;
        cardLayout.next(cardContainer);
    }

    public boolean isFront(){
        return isFront;
    }
}
