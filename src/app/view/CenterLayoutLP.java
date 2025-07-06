package app.view;

import app.animation.AnimatedSprite;
import app.animation.StaticSprite;

import javax.swing.*;
import java.awt.*;

public class CenterLayoutLP extends JLayeredPane {
    private FlashcardHolderPanel flashcardHolderPanel;
    private AnimatedSprite animatedCat;
    private StaticSprite windowStaticSprite;
    private StaticSprite deskStaticSprite;
    private StaticSprite chairStaticSprite;

    public CenterLayoutLP() {
        setLayout(null);

        setup();
    }

    public void setup(){
        animatedCat = new AnimatedSprite(0, 0, "./src/Sprites/Cat");
        windowStaticSprite = new StaticSprite(0.5, 0.35, -325, 0, "./src/Sprites/Window.png");
        deskStaticSprite = new StaticSprite(0.5, 0.67, 0, 0, "./src/Sprites/Desk.png");
        chairStaticSprite = new StaticSprite(0.5, 0.67, 0, 0, "./src/Sprites/Chair.png");

        add(animatedCat, DEFAULT_LAYER);
        add(windowStaticSprite, DEFAULT_LAYER);
        add(deskStaticSprite, DEFAULT_LAYER);
        add(chairStaticSprite, DEFAULT_LAYER + 1);

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
        deskStaticSprite.setBounds(0, 0, size.width, size.height);
        deskStaticSprite.recalculatePositionAndScale(size.width, size.height);
        chairStaticSprite.setBounds(0, 0, size.width, size.height);
        chairStaticSprite.recalculatePositionAndScale(size.width, size.height);
    }
}
