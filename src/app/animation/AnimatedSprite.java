package app.animation;

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

    public AnimatedSprite(String path) {
        super(path);

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

        int targetX = edge.to.getPosition().x;
        int targetY = edge.to.getPosition().y;

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

    private void walk(int targetX, Timer t, Edge edge, Runnable onFinished){
        if (Math.abs(positionX - targetX) > walkingSpeed) {
            // Move toward target
            if (positionX < targetX) {
                facingLeft = false;
                positionX += walkingSpeed;
            } else {
                facingLeft = true;
                positionX -= walkingSpeed;
            }
            repaint();
        } else {
            // Final step: snap to target and stop
            positionX = targetX;
            t.stop();
            currentNodeId = edge.to;

            if (onFinished != null) {
                onFinished.run();
            }
        }
    }

    private void leap(int targetX, int targetY, Timer t, Edge edge, Runnable onFinished){
        double deltaX = targetX - positionX;
        double deltaY = targetY - positionY;
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
                positionX += dirX * jumpingSpeed*10;
                positionY += dirY * jumpingSpeed*10;
                isJumping = true;
            }
            else {
                positionX += dirX * jumpingSpeed * 1.2;
                positionY += dirY * jumpingSpeed * 1.2;
            }
            repaint();
        }
        else {
            positionX = targetX;
            positionY = targetY;
            t.stop();
            currentNodeId = edge.to;
            isJumping = false;
            if(onFinished != null) onFinished.run();
        }
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
        setPosition(NodeId.WINDOWBOARD_LEFT.getPosition().x, NodeId.WINDOWBOARD_LEFT.getPosition().y);
        System.out.println(NodeId.WINDOWBOARD_LEFT.getPosition().x + " " + NodeId.WINDOWBOARD_LEFT.getPosition().y);
    }

    public void moveTo(NodeId goadlId){
        movementEngine.moveTo(goadlId);
        System.out.println("Test");
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

    public void setBackgroundOffSetX(int backgroundOffSetX){
        //this.offsetX = backgroundOffSetX;
    }

    public void setFrameTypeIdle(){
        currentFrames = idleFrames;
    }

    @Override
    public void recalculatePositionAndScale(int width, int height) {
        // When screen size changes, update the position and scale relative to screen size
        this.screenWidth = width;
        this.screenHeight = height;

        positionScaleX = (double) screenWidth / 1920;
        positionScaleY = (double) screenHeight / 1080;
        spriteScale = (double) screenHeight / 1080;

//        scaledOffsetX = (int) (offsetX * scale);
//        scaledOffsetY = (int) (offsetY * scale);
    }

    @Override
    public void setPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        currentFrame = currentFrames[currentFrameIndex % currentFrames.length];

        int calPositionX = positionX - (idleFrames[0].getWidth()/2) - offsetX;
        int calPositionY = positionY - idleFrames[0].getHeight();

        // Scale the position
        calPositionX = (int) (calPositionX * positionScaleX);
        calPositionY = (int) (calPositionY * positionScaleY);

        scaledWidth = (int) (currentFrame.getWidth() * spriteScale);
        scaledHeight = (int) (currentFrame.getHeight() * spriteScale);

        if(!facingLeft){
            g.drawImage(currentFrame, calPositionX, calPositionY, - scaledWidth, scaledHeight, null);
        }
        else{
            g.drawImage(currentFrame, calPositionX, calPositionY, scaledWidth, scaledHeight, null);
        }
    }
}
