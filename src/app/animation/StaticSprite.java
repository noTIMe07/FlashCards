package app.animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StaticSprite extends JComponent {
    protected double positionRatioX, positionRatioY;
    protected int positionX, positionY, scaledWidth, scaledHeight, scaledOffsetY, scaledOffsetX;
    protected double scale;
    protected int offsetX, offsetY;
    protected String path;

    private BufferedImage sprite;

    public StaticSprite(double positionRatioX, double positionRatioY, int offsetX, int offsetY) {
        // This constructor is used by all classes that extend StaticSprite, because no path is needed
        this.positionRatioX = positionRatioX;
        this.positionRatioY = positionRatioY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public StaticSprite(double positionRatioX, double positionRatioY, int offsetX, int offsetY, String path) {
        // This constructor is used by all sprite objects, to initialize the path
        this(positionRatioX, positionRatioY, offsetX, offsetY);
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
        positionX = (int) (positionRatioX * width);
        positionY = (int) (positionRatioY * height);

        scale = (double) height/1080;
        scaledWidth = (int) (sprite.getWidth() * scale);
        scaledHeight = (int) (sprite.getHeight() * scale);
        scaledOffsetX = (int) (offsetX * scale);
        scaledOffsetY = (int) (offsetY * scale);
    }

    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
        repaint();
    }

    // getPosition returns point at Index, most left point is index 0
    public Point getPosition(){
        System.out.println(positionX + " " + positionY);
        return new Point(positionX, positionY);
    }

    public double getScale(){
        return scale;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (path == null || sprite == null) return;

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        // The sprite coordinate is the center of the sprite
        int calPositionX = positionX - (scaledWidth / 2) + scaledOffsetX;
        int calPositionY = positionY - (scaledHeight / 2) + scaledOffsetY;

        g2d.drawImage(sprite, calPositionX, calPositionY, scaledWidth, scaledHeight, null);

        g2d.dispose();
    }

}
