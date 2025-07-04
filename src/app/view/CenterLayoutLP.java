package app.view;

import app.animation.AnimatedSprite;
import app.animation.StaticSprite;

import javax.swing.*;
import java.awt.*;

public class CenterLayoutLP extends JLayeredPane {
    private FlashcardHolderPanel flashcardHolderPanel;
    private AnimatedSprite animatedCat;
    private StaticSprite windowStaticSprite;

    public CenterLayoutLP() {
        setLayout(null);

        setup();
    }

    public void setup(){
        animatedCat = new AnimatedSprite(0, 0, "./src/Sprites/Cat");
        windowStaticSprite = new StaticSprite(0, 0, "./src/Sprites/Window.png");

        add(animatedCat, DEFAULT_LAYER);
        add(windowStaticSprite, DEFAULT_LAYER);

        flashcardHolderPanel = new FlashcardHolderPanel(animatedCat);
        flashcardHolderPanel.setOpaque(false);
        add(flashcardHolderPanel, PALETTE_LAYER);
    }

    public void setFlashcardVisibility(boolean visibility){
        flashcardHolderPanel.setFlashcardVisibility(visibility);
    }

    @Override
    public void doLayout() {
        //Update bounds and sizes
        Dimension size = getSize();
        flashcardHolderPanel.setBounds(0, 0, size.width, size.height);
        animatedCat.setBounds(0, 0, size.width, size.height);
        windowStaticSprite.setBounds(0, 0, size.width, size.height);
        windowStaticSprite.recalculatePositionAndScale(size.width, size.height);
    }
}
