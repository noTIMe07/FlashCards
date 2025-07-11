package app.animation;

import javax.swing.*;

public abstract class Sprite extends JComponent {
    protected String path;
    protected int positionX, positionY, calPositionX, calPositionY;
    protected int screenWidth, screenHeight;
    protected int scaledWidth, scaledHeight;
    protected int offsetX;
    protected double spriteScale, positionScaleX, positionScaleY;

    public Sprite(String path){
        this.path = path;

        screenWidth = 1920;
        screenHeight = 1080;
    }

    public abstract void recalculatePositionAndScale(int width, int height);

    public abstract void setPosition(int positionX, int positionY);
}
