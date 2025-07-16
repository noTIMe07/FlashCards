package app.animation;

import app.view.CenterLayoutLP;

import javax.swing.*;

public abstract class Sprite extends JComponent {
    protected String path;
    protected CenterLayoutLP layoutLP;
    protected int centerOffsetX, centerOffsetY, backgroundOffsetX, calPositionX, calPositionY;
    protected int screenWidth, screenHeight;
    protected int scaledWidth, scaledHeight;
    protected double spriteScale, offsetScaleX, offsetScaleY;

    public Sprite(String path, CenterLayoutLP layoutLP){
        this.path = path;
        this.layoutLP = layoutLP;

        screenWidth = 1920;
        screenHeight = 1080;
    }

    public abstract void setBackgroundOffSetX(int backgroundOffSetX);

    public abstract void recalculatePositionAndScale(int width, int height);

    public abstract void setCenterOffset(int positionX, int positionY);
}
