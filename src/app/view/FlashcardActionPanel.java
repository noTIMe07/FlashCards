package app.view;

import app.Style;

import javax.swing.*;
import java.awt.*;

public class FlashcardActionPanel extends JPanel {

    private JActionButton addFlashcardButton;
    private JActionButton removeFlashcardButton;
    private JActionButton editFlashcardButton;
    private FlashcardHolderPanel flashcardHolderPanel;

    public FlashcardActionPanel(FlashcardHolderPanel flashcardHolderPanel){
        this.flashcardHolderPanel = flashcardHolderPanel;

        setLayout();
        setupButtons();
    }

    public void setLayout(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, Style.OUTLINE_COLOR),
                BorderFactory.createEmptyBorder(10, 0, 10,0 )
        ));
        setBackground(Style.SIDEBARPANEL_COLOR);
        setOpaque(true);
        setPreferredSize(new Dimension(340, 85));
        setMaximumSize(new Dimension(340, 85));
        setMaximumSize(new Dimension(340, 85));

        JPanel buttonHolderPanel = new JPanel();
        buttonHolderPanel.setOpaque(false);
        setLayout(new GridLayout(1, 3, 4, 0));

        addFlashcardButton = new JActionButton("Add", "/Icons/flashcard_add.png");
        removeFlashcardButton = new JActionButton("Remove", "/Icons/flashcard_remove.png");
        editFlashcardButton = new JActionButton("Edit", "/Icons/flashcard_edit.png");

        buttonHolderPanel.add(addFlashcardButton);
        buttonHolderPanel.add(removeFlashcardButton);
        buttonHolderPanel.add(editFlashcardButton);

        add(buttonHolderPanel);
    }

    private void setupButtons(){
        addFlashcardButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType().equals(FlashcardPanelType.FLASHCARD)){
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.ADD);
            }
        });

        removeFlashcardButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType().equals(FlashcardPanelType.FLASHCARD) && !flashcardHolderPanel.isDeckLearned()){
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.REMOVE);
            }
        });

        editFlashcardButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType().equals(FlashcardPanelType.FLASHCARD) && !flashcardHolderPanel.isDeckLearned()){
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.EDIT);
            }
        });


    }

    // 1 for add button activated, 2 for delete, 3 for edit, 0 for all
    public void styleButton(int button){
        if(button==0){
            addFlashcardButton.setActionButtonActive(false);
            removeFlashcardButton.setActionButtonActive(false);
            editFlashcardButton.setActionButtonActive(false);
        }
        else{
            if (button == 1) addFlashcardButton.setActionButtonActive(true);
            else addFlashcardButton.setActionButtonActive(false);
            if (button == 2) removeFlashcardButton.setActionButtonActive(true);
            else removeFlashcardButton.setActionButtonActive(false);
            if (button == 3) editFlashcardButton.setActionButtonActive(true);
            else editFlashcardButton.setActionButtonActive(false);
        }
    }
}
