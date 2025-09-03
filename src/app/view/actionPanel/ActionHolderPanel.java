package app.view.actionPanel;

import app.Style;
import app.view.flashcardPanel.FlashcardHolderPanel;

import javax.swing.*;
import java.awt.*;

public class ActionHolderPanel extends JPanel {
    private CatActionPanel catActionPanel;
    private FlashcardActionPanel flashcardActionPanel;
    private FlashcardHolderPanel flashcardHolderPanel;
    private JPanel holderPanel;
    private CardLayout cardLayout;

    public ActionHolderPanel(FlashcardHolderPanel flashcardHolderPanel){
        this.flashcardHolderPanel = flashcardHolderPanel;

        setup();
    }

    public void styleButton(int button){
        flashcardActionPanel.styleButton(button);
        catActionPanel.styleButton(button);
    }

    public void flip(){
        cardLayout.next(holderPanel);
    }

    public void setup(){
        setLayout(new GridBagLayout());
        setBackground(Style.SIDEBARPANEL_COLOR);

        catActionPanel = new CatActionPanel(flashcardHolderPanel);
        flashcardActionPanel = new FlashcardActionPanel(flashcardHolderPanel);

        cardLayout = new CardLayout();
        holderPanel = new JPanel(cardLayout);

        holderPanel.add(catActionPanel, "CatActionPanel");
        holderPanel.add(flashcardActionPanel, "FlashcardActionPanel");

        add(holderPanel);
    }
}
