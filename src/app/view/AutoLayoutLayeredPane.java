package app.view;

import app.animation.AnimatedSprite;

import javax.swing.*;
import java.awt.*;

public class AutoLayoutLayeredPane extends JLayeredPane {
    private final JPanel contentHolder;
    private final AnimatedSprite animatedCat;

    public AutoLayoutLayeredPane(JPanel contentHolder, AnimatedSprite animatedCat) {
        this.contentHolder = contentHolder;
        this.animatedCat = animatedCat;
        setLayout(null);
        add(contentHolder, DEFAULT_LAYER);
        add(animatedCat, PALETTE_LAYER);
    }

    @Override
    public void doLayout() {
        Dimension size = getSize();
        contentHolder.setBounds(0, 0, size.width, size.height);
        animatedCat.setBounds(0, 0, size.width, size.height);
    }
}
