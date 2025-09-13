package app.view.flashcardPanel.catTrailPanel;

import app.Style;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrailPanel extends JPanel {
    private int flashcardsStudied;
    private int maxMilestone;
    private int customWidth;
    private int customHeigth;

    private final Timer timer;
    private final Random random;

    private final int milestonePixelSpacing = 300;
    private final int milestoneSpacing = 250;
    private int tick;
    private final List<Lootbox> lootboxes = new ArrayList<>();

    private final int frameHeight = 192;

    public TrailPanel(int height, int flashcardsStudied, int maxMilestone) {
        this.customWidth = maxMilestone * milestonePixelSpacing / milestoneSpacing;
        this.customHeigth = height;
        this.flashcardsStudied = flashcardsStudied;
        this.maxMilestone = maxMilestone;
        random = new Random();
        tick = 0;

        setPreferredSize(new Dimension(customWidth + 300, customHeigth));
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
                int r = random.nextInt(200);
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
        for (int x = 50 + milestonePixelSpacing; x <= 50 + customWidth; x += milestonePixelSpacing){
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
        if (percent > 1) percent = 1;
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

        int milestoneCount = 1;

        int counter = 0;
        for (int x = 50 + milestonePixelSpacing; x <= 50 + customWidth; x += milestonePixelSpacing) {
            // Shadow oval
            if(x <= 50 + progressWidth) g2.setColor(new Color(89,89,89));
            else g2.setColor(new Color(200, 200, 200));
            g2.fillOval(x - 100, (y/2) + 10, 200, 80);

            // Lootbox above circle
            if (counter < lootboxes.size()) {
                lootboxes.get(counter).draw(g2, x, y/2);
            }
            counter++;

            // Outer circle
            g2.setColor(Color.BLACK);
            g2.fillOval(x - outerRadius, y - outerRadius, outerDiameter, outerDiameter);

            // Inner circle
            if(x <= 50 + progressWidth) g2.setColor(Style.ADDFOLDERBUTTON_COLOR);
            else g2.setColor(new Color(200, 200, 200));
            g2.fillOval(x - innerRadius, y - innerRadius, innerDiameter, innerDiameter);

            // Milestone text
            String text = String.valueOf(milestoneSpacing * milestoneCount++);
            int textWidth = fm.stringWidth(text);
            int textX = x - (textWidth / 2);
            int textY = y + outerRadius + fm.getAscent() + 5;

            g2.setColor(Color.BLACK);
            g2.drawString(text, textX, textY);
        }

        g2.dispose();
    }
}
