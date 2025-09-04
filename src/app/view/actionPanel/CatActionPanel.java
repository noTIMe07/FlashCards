package app.view.actionPanel;


import app.Style;
import app.controller.FolderController;
import app.view.flashcardPanel.FlashcardHolderPanel;
import app.view.flashcardPanel.FlashcardPanelType;

import javax.swing.*;
import java.awt.*;

public class CatActionPanel extends JPanel {
    private ActionButton catTrailButton;
    private ActionButton inventoryButton;
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

        catTrailButton = new ActionButton("Cat Trail", "/Icons/flashcard_add.png");
        inventoryButton = new ActionButton("Inventory", "/Icons/flashcard_add.png");

        buttonHolder.add(inventoryButton);
        buttonHolder.add(catTrailButton);

        add(buttonHolder);
    }

    private void setupButtons(){
        inventoryButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType() == FlashcardPanelType.INVENTORY){
                flashcardHolderPanel.setFlashcardVisibility(false);
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
            }
            else flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.INVENTORY);
        });

        catTrailButton.addActionListener(e -> {
            if(flashcardHolderPanel.getFlashcardPanelType() == FlashcardPanelType.TRAIL){
                flashcardHolderPanel.setFlashcardVisibility(false);
                flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.FLASHCARD);
            }
            else flashcardHolderPanel.setFlashcardPanelType(FlashcardPanelType.TRAIL);
        });
    }

    // 1 for add button activated, 0 for all
    public void styleButton(int button){
        System.out.println(button);
        if(button==0){
            inventoryButton.setActionButtonActive(false);
            catTrailButton.setActionButtonActive(false);
        }
        else{
            if (button == 4) catTrailButton.setActionButtonActive(true);
            else catTrailButton.setActionButtonActive(false);
            if (button == 5) inventoryButton.setActionButtonActive(true);
            else inventoryButton.setActionButtonActive(false);
        }
    }
}
