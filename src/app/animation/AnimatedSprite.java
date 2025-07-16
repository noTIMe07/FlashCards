package app.animation;

import app.view.CenterLayoutLP;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimatedSprite extends Sprite{
    private final BufferedImage[] idleFrames;
    private final BufferedImage[] walkFrames;
    private final BufferedImage[] jumpDownFrames;
    private final BufferedImage[] jumpUpFrames;
    private final BufferedImage[] leapFrames;
    private final BufferedImage[] sleepFrames;
    private final BufferedImage[] wakingUpFrames;
    private final BufferedImage[] fallingAsleepFrames;
    private BufferedImage[] currentFrames;

    // Current Frame Index is the current index of BufferedImage[]
    private int currentFrameIndex;
    private BufferedImage currentFrame;

    // Frame delay for Sprite and animation
    private final int SpriteFrameDelay = 120;
    private final int AnimationFrameDelay = 16;

    private final Timer timer;
    private NodeId currentNodeId;
    private final SpriteMovementEngine movementEngine;

    private int screenWidth, screenHeight;

    private boolean facingLeft;
    private boolean isJumping;
    private boolean isAsleep;
    private final int walkingSpeed = 3 ;
    private final int jumpingSpeed = 5;

    //Keeps track if certain frames are played to make sure that every necessary frame is shown
    boolean framePlayed;

    public AnimatedSprite(String path, CenterLayoutLP layoutLP) {
        super(path, layoutLP);

        currentNodeId = NodeId.WINDOWBOARD_LEFT;
        movementEngine = new SpriteMovementEngine(this);
        idleFrames = loadFrames(path + "/IDLE.png", 480, 384, 8);
        walkFrames = loadFrames(path + "/WALK.png", 480, 384, 12);
        jumpDownFrames = loadFrames(path + "/JUMP_DOWN.png", 480, 384, 1);
        jumpUpFrames = loadFrames(path + "/JUMP_UP.png", 480, 384, 1);
        leapFrames = loadFrames(path + "/LEAP.png", 480, 384, 1);
        sleepFrames = loadFrames(path + "/SLEEP.png", 480, 384, 8);
        wakingUpFrames = loadFrames(path + "/WAKING_UP.png", 480, 384, 3);
        fallingAsleepFrames = loadFrames(path + "/FALLING_ASLEEP.png", 480, 384, 3);
        currentFrames = idleFrames;
        facingLeft = false;
        isJumping = false;
        isAsleep = false;

        framePlayed = false;

        currentFrameIndex = 0;
        timer = new Timer(SpriteFrameDelay, e -> {
            currentFrameIndex += 1;
            repaint();
        });
        timer.start();


        new Timer(500, e -> {
            ((Timer)e.getSource()).stop();
            sitOnTheWindowBoard();
        }).start();
    }

    public void playMovement(Edge edge, Runnable onFinished) {
        Timer movementTimer = new Timer(AnimationFrameDelay, null);

        layoutLP.setCatLayer(edge.layerIndex);

        int targetX = edge.to.getCenterOffset().x;
        int targetY = edge.to.getCenterOffset().y;

        switch (edge.movementType){
            case SIT: currentFrames = idleFrames;
            break;
            case WALK: currentFrames = walkFrames;
            break;
            case JUMP_DOWN: currentFrames = jumpDownFrames;
            break;
            case JUMP_UP: currentFrames = jumpUpFrames;
            break;
            default: currentFrames = idleFrames;
        }
        currentFrameIndex = 0;

        movementTimer.addActionListener(e -> {
            if( edge.movementType == MovementType.SIT){
                sit((Timer) e.getSource(), edge, onFinished);
            }
            else if (edge.movementType == MovementType.WALK) {
                walk(targetX, (Timer) e.getSource(), edge, onFinished);
            }
            else if(edge.movementType == MovementType.JUMP_DOWN || edge.movementType == MovementType.JUMP_UP) {
                leap(targetX, targetY, (Timer) e.getSource(), edge, onFinished);
            }
        });

        movementTimer.start();
    }

    private void walk(int targetOffsetX, Timer t, Edge edge, Runnable onFinished){
        if (Math.abs(centerOffsetX - targetOffsetX) > walkingSpeed) {
            // Move toward target
            if (centerOffsetX < targetOffsetX) {
                facingLeft = false;
                centerOffsetX += walkingSpeed;
            } else {
                facingLeft = true;
                centerOffsetX -= walkingSpeed;
            }
        } else {
            // Final step: snap to target and stop
            centerOffsetX = targetOffsetX;
            t.stop();
            currentNodeId = edge.to;

            if (onFinished != null) {
                onFinished.run();
            }
        }
        recalculatePositionAndScale(screenWidth, screenHeight);
        repaint();
    }

    private void leap(int targetOffsetX, int targetOffsetY, Timer t, Edge edge, Runnable onFinished){
        double deltaX = targetOffsetX - centerOffsetX;
        double deltaY = targetOffsetY - centerOffsetY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // If delta X > 0, then Sprite has to move right, so facing Left is false
        facingLeft = !(deltaX > 0);

        if(distance > 40) {
            if(distance < 80 && edge.movementType==MovementType.JUMP_UP){
                currentFrames = leapFrames;
            }
            double dirX = deltaX / distance;
            double dirY = deltaY / distance;
            if(isJumping==false && edge.movementType == MovementType.JUMP_DOWN){
                centerOffsetX += dirX * jumpingSpeed*10;
                centerOffsetY += dirY * jumpingSpeed*10;
                isJumping = true;
            }
            else {
                centerOffsetX += dirX * jumpingSpeed * 1.2;
                centerOffsetY += dirY * jumpingSpeed * 1.2;
            }
        }
        else {
            centerOffsetX = targetOffsetX;
            centerOffsetY = targetOffsetY;
            t.stop();
            currentNodeId = edge.to;
            isJumping = false;
            if(onFinished != null) onFinished.run();
        }
        recalculatePositionAndScale(screenWidth, screenHeight);
        repaint();
    }

    public void fallAsleep(){
        facingLeft = true;
        currentFrameIndex = 0;
        Timer t = new Timer(AnimationFrameDelay, e -> {
            if(currentFrameIndex < 2 && currentFrames != fallingAsleepFrames){
                currentFrames = fallingAsleepFrames;
            } else if (currentFrameIndex >= 2) {
                ((Timer) e.getSource()).stop();
                currentFrames = sleepFrames;
                isAsleep = true;
            }
        });
        t.start();
    }

    public void wakeUp(Runnable onFinished){
        currentFrameIndex = 0;
        Timer t = new Timer(AnimationFrameDelay, e -> {
            if(currentFrameIndex < 2 && currentFrames != wakingUpFrames){
                currentFrames = wakingUpFrames;
            } else if (currentFrameIndex >= 2) {
                isAsleep = false;
                currentFrames = idleFrames;
                if(currentFrameIndex >=20){
                    ((Timer) e.getSource()).stop();
                    if(onFinished != null) onFinished.run();
                }
            }
        });
        t.start();
    }

    private void sit(Timer t, Edge edge, Runnable onFinished){
        if(currentFrameIndex == 20){
            t.stop();
            currentNodeId = edge.to;

            if (onFinished != null) {
                onFinished.run();
            }
        }
    }

    public void sitOnTheWindowBoard(){
        setCenterOffset(NodeId.WINDOWBOARD_LEFT.getCenterOffset().x, NodeId.WINDOWBOARD_LEFT.getCenterOffset().y);
    }

    public BufferedImage[] loadFrames(String path, int frameWidth, int frameHeight, int frameCount) {
        try{
            BufferedImage sheet = ImageIO.read(new File(path));
            BufferedImage[] frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                frames[i] = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }
            return frames;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NodeId getCurrentNodeId(){
        return currentNodeId;
    }

    public boolean isAsleep(){
        return isAsleep;
    }

    public void setFrameTypeIdle(){
        currentFrames = idleFrames;
    }

    @Override
    public void setBackgroundOffSetX(int backgroundOffSetX) {
        this.backgroundOffsetX = backgroundOffSetX;
        recalculatePositionAndScale(screenWidth, screenHeight);
    }

    @Override
    public void recalculatePositionAndScale(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height;

        // offset Scale is the ratio between the current screenWidth and the standard screenWidth
        offsetScaleX = (double) screenWidth / 1920;
        offsetScaleY = (double) screenHeight / 1080;
        spriteScale = (double) screenHeight / 1080;
        scaledWidth = (int) (currentFrames[0].getWidth() * spriteScale);
        scaledHeight = (int) (currentFrames[0].getHeight() * spriteScale);

        calPositionX = (int) (960 * offsetScaleX);
        calPositionY = (int) (540 * offsetScaleY);

        // The sprite coordinate is the bottom center of the sprite
        calPositionX = calPositionX + (scaledWidth / 2);
        calPositionY = calPositionY - (scaledHeight / 2);

        calPositionX += (int) ((centerOffsetX + backgroundOffsetX) * offsetScaleY);
        calPositionY += (int) (centerOffsetY * offsetScaleY);

        repaint();
    }

    @Override
    public void setCenterOffset(int centerOffsetX, int centerOffsetY) {
        this.centerOffsetX = centerOffsetX;
        this.centerOffsetY = centerOffsetY;

        recalculatePositionAndScale(screenWidth, screenHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentFrame = currentFrames[currentFrameIndex % currentFrames.length];

        if(!facingLeft){
            g.drawImage(currentFrame, calPositionX, calPositionY, - scaledWidth, scaledHeight, null);
        }
        else{
            g.drawImage(currentFrame, calPositionX - scaledWidth, calPositionY, scaledWidth, scaledHeight, null);
        }
    }
}
