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

    private Timer backgroundScrollTimer;
    private boolean isAnimatingBackground;

    public CenterLayoutLP() {
        setLayout(null);

        setup();
    }

    public void setup(){
        isAnimatingBackground = false;

        NodeId[] idList = new NodeId[] { WINDOWBOARD_LEFT, WINDOWBOARD_RIGHT };
        windowStaticSprite = new StaticSprite(-300, -150, "./src/Sprites/Window.png", idList);
        WINDOWBOARD_LEFT.setSprite(windowStaticSprite);
        WINDOWBOARD_RIGHT.setSprite(windowStaticSprite);
        idList = new NodeId[] { TABLE_LEFT, TABLE_MIDDLE, TABLE_RIGHT};
        tableStaticSprite = new StaticSprite( 0, 200,"./src/Sprites/Table.png", idList);
        TABLE_LEFT.setSprite(tableStaticSprite);
        TABLE_MIDDLE.setSprite(tableStaticSprite);
        TABLE_RIGHT.setSprite(tableStaticSprite);
        idList = new NodeId[] {CHAIR, CHAIR_SIT};
        chairStaticSprite = new StaticSprite( 0, 200,"./src/Sprites/Chair.png", idList);
        CHAIR.setSprite(chairStaticSprite);
        CHAIR_SIT.setSprite(chairStaticSprite);

        animatedCat = new AnimatedSprite("./src/Sprites/Cat", this);

        add(animatedCat, DEFAULT_LAYER);
        add(windowStaticSprite, DEFAULT_LAYER);
        add(tableStaticSprite, DEFAULT_LAYER);
        add(chairStaticSprite, Integer.valueOf(2));

        flashcardHolderPanel = new FlashcardHolderPanel(this);
        flashcardHolderPanel.setOpaque(false);
        add(flashcardHolderPanel, PALETTE_LAYER);
    }

    public void playBackgroundScrollAnimation(int durationMillis, Runnable onComplete) {
        if (isAnimatingBackground) return;

        isAnimatingBackground = true;

        long startTime = System.currentTimeMillis();

        backgroundScrollTimer = new Timer(16, null);
        backgroundScrollTimer.addActionListener(e -> {

            long elapsed = System.currentTimeMillis() - startTime;
            double t = Math.min(1.0, (double) elapsed / durationMillis);

            // Easing function: ease-in-out
            double easedProgress = easeInOutQuart(t);

            if (easedProgress >= 1) {
                backgroundScrollTimer.stop();
                setBackgroundOffsetX(- 1920);
                isAnimatingBackground = false;

                if (onComplete != null) onComplete.run();
            } else {
                setBackgroundOffsetX((int) (easedProgress * - 1920));
            }
            repaint();
        });

        backgroundScrollTimer.start();
    }

    private double easeInOutQuart(double x) {
        return x < 0.5
                ? 8 * Math.pow(x, 4)
                : 1 - Math.pow(-2 * x + 2, 4) / 2;
    }

    public void setFlashcardVisibility(boolean visibility){
        flashcardHolderPanel.setFlashcardVisibility(visibility);
    }

    public void setCatLayer(int layerIndex){
        setLayer(animatedCat, Integer.valueOf(layerIndex));
    }

    private void setBackgroundOffsetX(int backgroundOffsetX){
        animatedCat.setBackgroundOffSetX(backgroundOffsetX);
        windowStaticSprite.setBackgroundOffSetX(backgroundOffsetX);
        tableStaticSprite.setBackgroundOffSetX(backgroundOffsetX);
        chairStaticSprite.setBackgroundOffSetX(backgroundOffsetX);
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
