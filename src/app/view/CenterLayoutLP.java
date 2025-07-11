package app.view;

import app.animation.AnimatedSprite;
import app.animation.NodeId;
import app.animation.StaticSprite;

import javax.swing.*;
import java.awt.*;

import static app.animation.NodeId.*;

public class CenterLayoutLP extends JLayeredPane {
    private FlashcardHolderPanel flashcardHolderPanel;
    private AnimatedSprite animatedCat;
    private StaticSprite windowStaticSprite;
    private StaticSprite tableStaticSprite;
    private StaticSprite chairStaticSprite;

    public CenterLayoutLP() {
        setLayout(null);

        setup();
    }

    public void setup(){
        NodeId[] idList;
        idList = new NodeId[] { WINDOWBOARD_LEFT, WINDOWBOARD_RIGHT };
        windowStaticSprite = new StaticSprite(960, 380, -300, "./src/Sprites/Window.png", idList);
        WINDOWBOARD_LEFT.setSprite(windowStaticSprite);
        WINDOWBOARD_RIGHT.setSprite(windowStaticSprite);
        idList = new NodeId[] { TABLE_LEFT, TABLE_MIDDLE, TABLE_RIGHT};
        tableStaticSprite = new StaticSprite(960, 720, 0,"./src/Sprites/Table.png", idList);
        TABLE_LEFT.setSprite(tableStaticSprite);
        TABLE_MIDDLE.setSprite(tableStaticSprite);
        TABLE_RIGHT.setSprite(tableStaticSprite);
        idList = new NodeId[] {CHAIR, CHAIR_SIT};
        chairStaticSprite = new StaticSprite(960, 720, 0,"./src/Sprites/Chair.png", idList);
        CHAIR.setSprite(chairStaticSprite);
        CHAIR_SIT.setSprite(chairStaticSprite);

        animatedCat = new AnimatedSprite("./src/Sprites/Cat");

        add(animatedCat, DEFAULT_LAYER);
        add(windowStaticSprite, DEFAULT_LAYER);
        add(tableStaticSprite, DEFAULT_LAYER);
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
        animatedCat.recalculatePositionAndScale(size.width, size.height);
        windowStaticSprite.setBounds(0, 0, size.width, size.height);
        windowStaticSprite.recalculatePositionAndScale(size.width, size.height);
        tableStaticSprite.setBounds(0, 0, size.width, size.height);
        tableStaticSprite.recalculatePositionAndScale(size.width, size.height);
        chairStaticSprite.setBounds(0, 0, size.width, size.height);
        chairStaticSprite.recalculatePositionAndScale(size.width, size.height);
    }
}
