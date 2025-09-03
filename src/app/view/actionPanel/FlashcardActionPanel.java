package app.view.actionPanel;

import app.Style;
import app.view.flashcardPanel.FlashcardHolderPanel;
import app.view.flashcardPanel.FlashcardPanelType;

import javax.swing.*;
import java.awt.*;

public class FlashcardActionPanel extends JPanel {

    private ActionButton addFlashcardButton;
    private ActionButton removeFlashcardButton;
    private ActionButton editFlashcardButton;
    private JPanel buttonHolder;
    private FlashcardHolderPanel flashcardHolderPanel;

    public FlashcardActionPanel(FlashcardHolderPanel flashcardHolderPanel){
        this.flashcardHolderPanel = flashcardHolderPanel;

        setLayout();
        setupButtons();
    }

    public void setLayout(){
        setLayout(new GridLayout(1, 3, 4, 0));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, Style.OUTLINE_COLOR),
                BorderFactory.createEmptyBorder(10, 0, 10,0 )
        ));
        setBackground(Style.SIDEBARPANEL_COLOR);
        setOpaque(true);

        buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);

        addFlashcardButton = new ActionButton("Add", "/Icons/flashcard_add.png");
        removeFlashcardButton = new ActionButton("Remove", "/Icons/flashcard_remove.png");
        editFlashcardButton = new ActionButton("Edit", "/Icons/flashcard_edit.png");

        buttonHolder.add(addFlashcardButton);
        buttonHolder.add(removeFlashcardButton);
        buttonHolder.add(editFlashcardButton);

        add(buttonHolder);
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
