package app.animation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StaticSprite extends Sprite {
    private BufferedImage sprite;
    private NodeId[] idList;

    public StaticSprite(int offsetX, int offsetY, String path, NodeId[] idList) {
        super(path, null);

        this.centerOffsetX = offsetX;
        this.centerOffsetY = offsetY;
        this.idList = idList;

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
        this.screenWidth = width;
        this.screenHeight = height;

        offsetScaleX = (double) screenWidth / 1920;
        offsetScaleY = (double) screenHeight / 1080;
        spriteScale = (double) screenHeight / 1080;
        scaledWidth = (int) (sprite.getWidth() * spriteScale);
        scaledHeight = (int) (sprite.getHeight() * spriteScale);

        calPositionX = (int) (960 * offsetScaleX);
        calPositionY = (int) (540 * offsetScaleY);

        // The sprite coordinate is the center of the sprite
        calPositionX = calPositionX - (scaledWidth / 2);
        calPositionY = calPositionY - (scaledHeight / 2);

        calPositionX += (int) ((centerOffsetX + backgroundOffsetX) * offsetScaleY);
        calPositionY += (int) (centerOffsetY * offsetScaleY);

        repaint();
    }

    public void setCenterOffset(int positionX, int positionY) {
//        this.positionX = positionX;
//        this.positionY = positionY;
//        repaint();
    }

    private void setIdPosition(){
        for(NodeId id : idList){
            //id.setRatioAndOffset(positionRatioX, positionRatioY, offsetX, offsetY);
        }
    }

    // getPosition returns point at Index, most left point is index 0
    public Point getCenterOffset(){
        return new Point(centerOffsetX, centerOffsetY);
    }

    public double getScale(){
        return spriteScale;
    }

    @Override
    public void setBackgroundOffSetX(int backgroundOffSetX) {
        this.backgroundOffsetX = backgroundOffSetX;
        recalculatePositionAndScale(screenWidth, screenHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (path == null || sprite == null) return;

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.drawImage(sprite, calPositionX , calPositionY, scaledWidth, scaledHeight, null);

        g2d.dispose();
    }

}
