package app.animation;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class SpriteMovementEngine {
    private AnimatedSprite cat;
    private NodeId goalNodeId;
    private Timer movementLoop;
    private Graph graph;
    private Random random;
    private int waitingTime;

    // Cat characteristics
    int laziness;
    int awakeTime;
    int minSleepingLength;

    public SpriteMovementEngine(AnimatedSprite cat){
        this.cat=cat;
        graph = new Graph();
        random = new Random();
        goalNodeId = NodeId.WINDOWBOARD_LEFT;
        waitingTime = 0;

        // Cat characteristics
        laziness = 50; //30 usually
        awakeTime = 8;
        minSleepingLength = 5;


        // Cat doesnt move after sleep
        // Cat moves during sleep

        movementLoop = new Timer(1000, e -> {
            if (cat.getCurrentNodeId() == goalNodeId) {
                if(random.nextInt(laziness) < waitingTime) {
                    waitingTime = 0;
                    goalNodeId = NodeId.values()[random.nextInt(NodeId.values().length)];
                    System.out.println(goalNodeId);
                    if(cat.isAsleep()){
                        cat.wakeUp(()->{moveTo(goalNodeId);});
                    }
                    else moveTo(goalNodeId);
                }
                else if(waitingTime > random.nextInt(awakeTime * 2) + awakeTime){
                    cat.fallAsleep();
                    waitingTime = - minSleepingLength;
                }
                else {
                    waitingTime ++;
                }
            }
        });
        movementLoop.start();
    }

    public void moveTo(NodeId targetId) {
        List<Edge> path = findPathTo(targetId);
        playAnimationSequence(path);
    }

    private List<Edge> findPathTo(NodeId targetId) {
        return graph.findPath(cat.getCurrentNodeId(), targetId);
    }

    private void playAnimationSequence(List<Edge> path) {
        // Base case
        if (path.isEmpty()){
            cat.setFrameTypeIdle();
            return;
        }

        Edge currentEdge = path.remove(0);
        cat.playMovement(currentEdge, () -> playAnimationSequence(path));
    }
}

