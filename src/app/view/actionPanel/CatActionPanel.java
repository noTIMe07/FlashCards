package app.view.actionPanel;


import app.Style;
import app.controller.FolderController;
import app.view.flashcardPanel.FlashcardHolderPanel;
import app.view.flashcardPanel.FlashcardPanelType;

import javax.swing.*;
import java.awt.*;

public class CatActionPanel extends JPanel {
    private ActionButton settingsButton;
    private ActionButton addFlashcardButton;
    private JPanel buttonHolder;
    private FlashcardHolderPanel flashcardHolderPanel;

    public CatActionPanel(FlashcardHolderPanel flashcardHolderPanel){
        this.flashcardHolderPanel = flashcardHolderPanel;
        setup();
        setupButtons();
    }

    public void setup(){
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

        buttonHolder = new JPanel();
        buttonHolder.setOpaque(false);
        setLayout(new GridLayout(1, 3, 4, 0));

        settingsButton = new ActionButton("Settings", "/Icons/flashcard_add.png");
        addFlashcardButton = new ActionButton("Add", "/Icons/flashcard_add.png");

        buttonHolder.add(addFlashcardButton);
        buttonHolder.add(settingsButton);

        add(buttonHolder);
    }

    private void setupButtons(){
        addFlashcardButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType().equals(FlashcardPanelType.FLASHCARD)){
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.ADD);
            }
        });
    }

    // 1 for add button activated, 0 for all
    public void styleButton(int button){
        if(FolderController.getCurrentFolderPath().equals("./src/FlashcardStorage/Default.json")) return;

        if(button==0){
            addFlashcardButton.setActionButtonActive(false);
        }
        else{
            if (button == 1) addFlashcardButton.setActionButtonActive(true);
            else addFlashcardButton.setActionButtonActive(false);
        }
    }
}
