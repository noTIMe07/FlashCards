package app.view;

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
    private MainPanel mainPanel;

    public FlashcardPanel(String question, String answer, MainPanel mainPanel_) {
        mainPanel = mainPanel_;

        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(1000, 700));

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

        // Front Flip Button
        flipButton = new JButton("Flip");
        flipButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        flipButton.setBackground(new Color(255, 149, 128));
        flipButton.setForeground(Color.WHITE);
        flipButton.setFocusPainted(false);
        flipButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        frontCard.add(flipButton, BorderLayout.SOUTH);

        // Back (answer)
        answerLabel = createCardLabel(answer);
        JPanel backCard = new JPanel(new BorderLayout());
        backCard.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        backCard.setBorder(BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true));
        backCard.add(answerLabel, BorderLayout.CENTER);

        //Back Card buttons
        JPanel backCardButtons = new JPanel(new BorderLayout());

        //reverseButton
        reverseButton = new JButton("←");
        reverseButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        reverseButton.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        reverseButton.setFocusPainted(false);
        reverseButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        wrongButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        answerButtons.add(wrongButton);

        //rightButton
        rightButton = new JButton("Right");
        rightButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        rightButton.setBackground(new Color(104, 166, 145));
        rightButton.setForeground(Color.WHITE);
        rightButton.setFocusPainted(false);
        rightButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        answerButtons.add(rightButton);

        //adding to Buttons and Cards to layout
        backCardButtons.add(answerButtons, BorderLayout.CENTER);
        backCard.add(backCardButtons, BorderLayout.SOUTH);
        cardContainer.add(frontCard, "question");
        cardContainer.add(backCard, "answer");
        add(cardContainer, BorderLayout.CENTER);

        //Button functionality
        centerButton.addActionListener(e -> cardLayout.next(cardContainer));
        flipButton.addActionListener(e -> cardLayout.next(cardContainer));
        reverseButton.addActionListener(e -> cardLayout.next(cardContainer));

        rightButton.addActionListener(e -> mainPanel.setLearned(true));
        wrongButton.addActionListener(e -> mainPanel.setLearned(false));
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
        cardLayout.first(cardContainer);
    }
}
