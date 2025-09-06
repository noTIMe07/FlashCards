package app.view.flashcardPanel.catTrailPanel;

import app.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class TrailPanel extends JPanel {
    private int flashcardsStudied;
    private int maxMilestone;
    private int customWidth;
    private int customHeigth;

    private final Timer timer;
    private final Random random;

    private final int step = 300;
    private int tick;
    private final List<Lootbox> lootboxes = new ArrayList<>();

    private final int frameWidth = 288;
    private final int frameHeight = 192;
    private final int totalFrames = 5;

    public TrailPanel(int width, int height, int flashcardsStudied, int maxMilestone) {
        this.customWidth = width;
        this.customHeigth = height;
        this.flashcardsStudied = flashcardsStudied;
        this.maxMilestone = maxMilestone;
        random = new Random();
        tick = 0;

        setPreferredSize(new Dimension(customWidth, customHeigth));
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        setLayout(null);

        createLootboxes();

        // Animation timer (changes frame every 200 ms)
        timer = new Timer(120, e -> {
            tick++;

            // Update all lootboxes
            for (Lootbox b : lootboxes) {
                b.update(tick);
            }

            // Randomly trigger exactly as you suggested:
            // pick r in [0, 1000); if r < lootboxes.size(), trigger that index
            if (!lootboxes.isEmpty()) {
                int r = random.nextInt(100);
                if (r < lootboxes.size()) {
                    Lootbox box = lootboxes.get(r);
                    if (!box.isAnimating()) {
                        box.playAnimation(tick);
                    }
                }
            }

            repaint();
        });
        timer.start();
    }

    private void createLootboxes(){
        int lootboxX;
        int lootboxY;
        for (int x = 50 + step; x < 50 + customWidth; x += step){
            lootboxX = x - 100;
            lootboxY = 600 - 20 - frameHeight - 150;
            lootboxes.add(new Lootbox(lootboxX, lootboxY, "./src/Sprites/Lootbox_Double.png"));
        }
    }

    public void setProgress(int studied) {
        this.flashcardsStudied = Math.min(studied, maxMilestone);
        repaint();
    }

    public void setMaxMilestone(int max) {
        this.maxMilestone = max;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = (int) (getHeight() * 0.75);

        // Background bar
        g2.setColor(new Color(200, 200, 200));
        g2.fillRoundRect(50, y - (customHeigth / 2), customWidth, customHeigth, customHeigth, customHeigth);

        // Progress
        double percent = (double) flashcardsStudied / maxMilestone;
        int progressWidth = (int) (customWidth * percent);
        g2.setColor(Style.ADDFOLDERBUTTON_COLOR);
        g2.fillRoundRect(50, y - (customHeigth / 2), progressWidth, customHeigth, customHeigth, customHeigth);

        // Circles + milestone labels
        int outerRadius = 20;
        int outerDiameter = outerRadius * 2;
        int innerRadius = 17;
        int innerDiameter = innerRadius * 2;

        Font font = new Font("Arial", Font.BOLD, 14);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();

        int milestonePoints = 250;
        int milestoneCount = 1;

        int counter = 0;
        for (int x = 50 + step; x < 50 + customWidth; x += step) {
            // Outer circle
            g2.setColor(Color.BLACK);
            g2.fillOval(x - outerRadius, y - outerRadius, outerDiameter, outerDiameter);

            // Inner circle
            g2.setColor(Style.ADDFOLDERBUTTON_COLOR);
            g2.fillOval(x - innerRadius, y - innerRadius, innerDiameter, innerDiameter);

            // Milestone text
            String text = String.valueOf(milestonePoints * milestoneCount++);
            int textWidth = fm.stringWidth(text);
            int textX = x - (textWidth / 2);
            int textY = y + outerRadius + fm.getAscent() + 5;

            g2.setColor(Color.BLACK);
            g2.drawString(text, textX, textY);

            // Lootbox above circle
            if (counter < lootboxes.size()) {
                lootboxes.get(counter).draw(g2, x, y/2);
            }
            counter++;
        }

        g2.dispose();
    }
}
