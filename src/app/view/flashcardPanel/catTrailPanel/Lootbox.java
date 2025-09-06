package app.view.flashcardPanel.catTrailPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Lootbox {
    // Shared sprite frames for all lootboxes (loaded once)
    private static BufferedImage[] FRAMES;
    private static final int FRAME_WIDTH = 288;
    private static final int FRAME_HEIGHT = 192;
    private static final int TOTAL_FRAMES = 10;

    // Animation settings (in ticks)
    private static final int FRAME_DURATION_TICKS = 1; // 1 frame per timer tick

    private int x, y; // top-left draw position
    private boolean animating = false;
    private int animationStartTick = 0;
    private int elapsedFrames = 0;

    public Lootbox(int x, int y, String spritePath) {
        this.x = x;
        this.y = y;
        ensureFramesLoaded(spritePath);
    }

    private static void ensureFramesLoaded(String path) {
        if (FRAMES != null) return;
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            FRAMES = new BufferedImage[TOTAL_FRAMES];
            // Your sheet uses HORIZONTAL frames: (i * width, 0)
            for (int i = 0; i < TOTAL_FRAMES; i++) {
                FRAMES[i] = sheet.getSubimage(i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load lootbox sprites: " + path, e);
        }
    }

    /** Trigger a one-shot animation if not already running. */
    public void playAnimation(int currentTick) {
        if (!animating) {
            animating = true;
            animationStartTick = currentTick;
        }
    }

    /** Call every tick from the TrailPanel timer to advance/stop the animation. */
    public void update(int currentTick) {
        if (!animating) return;
        elapsedFrames = currentTick - animationStartTick;
        if (elapsedFrames == TOTAL_FRAMES) {
            animating = false; // back to idle (frame 0)
            elapsedFrames = 0;
        }
    }

    /** Draws the current frame at (x,y). Uses frame 0 when idle. */
    public void draw(Graphics2D g2, int x, int y) {
        if (FRAMES == null) return;

        this.x = x;
        this.y = y;
        g2.drawImage(FRAMES[elapsedFrames], x - 100, y - 140, FRAME_WIDTH, FRAME_HEIGHT, null);
    }

    public boolean isAnimating() {
        return animating;
    }

    public void setPosition(int x, int y) {
        this.x = x; this.y = y;
    }
}
