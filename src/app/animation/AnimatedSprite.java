package app.animation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class AnimatedSprite extends StaticSprite{
    private BufferedImage[] idleFrames;
    private BufferedImage[] walkFrames;
    private BufferedImage[] jumpDownFrames;
    private BufferedImage[] jumpUpFrames;
    private BufferedImage[] leapFrames;
    private BufferedImage[] sleepFrames;
    private BufferedImage[] wakingUpFrames;
    private BufferedImage[] fallingAsleepFrames;
    private BufferedImage[] currentFrames;
    private int currentFrame;
    private int animationFrameDelay;
    private int frameDelay;
    private Timer timer;
    private NodeId currentNodeId;
    private SpriteMovementEngine movementEngine;
    private boolean facingLeft;
    private boolean isJumping;
    private boolean isAsleep;

    private final int walkingSpeed = 3 ;
    private final int jumpingSpeed = 5;

    //Keeps track if certain frames are played to make sure that every necessary frame is shows
    boolean framePlayed;

    public AnimatedSprite(int positionX, int positionY, String path) {
        super(positionX, positionY);
        this.path = path;

        currentFrame = 0;
        animationFrameDelay = 120;
        frameDelay = 16;
        offsetX = 0;
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
        timer = new Timer(animationFrameDelay, e -> {
            currentFrame += 1;
            repaint();
        });
        timer.start();

        sitOnTheWindowBoard();
    }

    public void playMovement(Edge edge, Runnable onFinished) {
        Timer movementTimer = new Timer(frameDelay, null);

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
        currentFrame = 0;

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

        if(deltaX > 0) facingLeft = false;
        else facingLeft = true;

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
        currentFrame = 0;
        Timer t = new Timer(frameDelay, e -> {
            if(currentFrame < 2 && currentFrames != fallingAsleepFrames){
                currentFrames = fallingAsleepFrames;
            } else if (currentFrame >= 2) {
                ((Timer) e.getSource()).stop();
                currentFrames = sleepFrames;
                isAsleep = true;
            }
        });
        t.start();
    }

    public void wakeUp(Runnable onFinished){
        currentFrame = 0;
        Timer t = new Timer(frameDelay, e -> {
            if(currentFrame < 2 && currentFrames != wakingUpFrames){
                currentFrames = wakingUpFrames;
            } else if (currentFrame >= 2) {
                isAsleep = false;
                currentFrames = idleFrames;
                if(currentFrame>=20){
                    ((Timer) e.getSource()).stop();
                    if(onFinished != null) onFinished.run();
                }
            }
        });
        t.start();
    }

    private void sit(Timer t, Edge edge, Runnable onFinished){
        if(currentFrame == 20){
            t.stop();
            currentNodeId = edge.to;

            if (onFinished != null) {
                onFinished.run();
            }
        }
    }

    public void sitOnTheWindowBoard(){
        setPosition(NodeId.WINDOWBOARD_LEFT.getPosition().x, NodeId.WINDOWBOARD_LEFT.getPosition().y);
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
        this.offsetX = backgroundOffSetX;
    }

    public void setFrameTypeIdle(){
        currentFrames = idleFrames;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int calPositionX = positionX - (idleFrames[0].getWidth()/2) - offsetX;
        int calPositionY = positionY - idleFrames[0].getHeight();

        if(!facingLeft){
            g.drawImage(currentFrames[currentFrame % currentFrames.length],
                    calPositionX + currentFrames[0].getWidth(), calPositionY,
                    -480, 384, null);
        }
        else{
            g.drawImage(currentFrames[currentFrame % currentFrames.length],
                    calPositionX, calPositionY, currentFrames[0].getWidth(), currentFrames[0].getHeight(),
                    null);
        }
    }
}
