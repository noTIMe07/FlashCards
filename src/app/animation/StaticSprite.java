package app.animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StaticSprite extends JComponent {
    protected int positionX, positionY, scaledWidth, scaledHeight;
    protected double scale;
    protected int offsetX;
    protected String path;

    private BufferedImage sprite;

    public StaticSprite(int positionX, int positionY) {
        // This constructor is used by all classes that extend StaticSprite, because no path is needed
        this.positionX = positionX;
        this.positionY = positionY;

        offsetX = 0;
    }

    public StaticSprite(int positionX, int positionY, String path) {
        // This constructor is used by all sprite objects, to initialize the path
        this(positionX, positionY);
        this.path = path;

        loadSprite();
    }

    private void loadSprite() {
        try {
            sprite = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void recalculatePositionAndScale(int width, int height) {
        // When screen size changes, update the position and scale relative to screen size
        positionX = (int) (0.5 * width);
        positionY = (int) (0.5 * height);

        scale = (double) height/1080;
        scaledWidth = (int) (sprite.getWidth() * scale);
        scaledHeight = (int) (sprite.getHeight() * scale);
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (path == null || sprite == null) return;

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // The sprite coordinate is the center of the sprite
        int calPositionX = positionX - (scaledWidth / 2) - offsetX;
        int calPositionY = positionY - (scaledHeight/2);

        g2d.drawImage(sprite, calPositionX, calPositionY, scaledWidth, scaledHeight, null);

        g2d.dispose();
    }

}
