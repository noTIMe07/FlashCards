package app.view;

import app.Style;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        Dimension zeroDim = new Dimension(0, 0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        if (!c.isEnabled()) return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Thumb color (handle)
        g2.setColor(Style.ADDFOLDERBUTTON_COLOR);
        g2.fillRect(r.x, r.y + 4, r.width, r.height - 4);

        // Left + right black borders (2px each)
        g2.setColor(Color.BLACK);
        g2.fillRect(r.x, r.y + 4, 2, r.height - 4);                  // left border
        g2.fillRect(r.x + r.width - 2, r.y + 4, 2, r.height - 4);    // right border

        // Only draw arrows + grip lines if thumb is wide enough
        if (r.width > 50) {
            // Grip lines (3 vertical lines)
            int spacing = 4;
            int centerX = r.x + r.width / 2;
            int topY = r.y + (r.height + 4) / 4;
            int bottomY = r.y + ((r.height + 4) * 3) / 4;

            for (int i = -1; i <= 1; i++) {
                int x = centerX + (i * spacing);
                g2.drawLine(x, topY, x, bottomY);
            }

            // Draw arrows (< and >) with 5px spacing from borders
            int arrowSize = Math.min((r.height + 4) / 3, 10); // scales with thumb
            int centerY = r.y + (r.height + 4) / 2;

            // Left arrow <
            Polygon leftArrow = new Polygon();
            leftArrow.addPoint(r.x + 2 + 5, centerY);                        // 2 border + 5 spacing
            leftArrow.addPoint(r.x + 2 + 5 + arrowSize, centerY - arrowSize);
            leftArrow.addPoint(r.x + 2 + 5 + arrowSize, centerY + arrowSize);
            g2.fillPolygon(leftArrow);

            // Right arrow >
            Polygon rightArrow = new Polygon();
            rightArrow.addPoint(r.x + r.width - 2 - 5, centerY);                        // -2 border -5 spacing
            rightArrow.addPoint(r.x + r.width - 2 - 5 - arrowSize, centerY - arrowSize);
            rightArrow.addPoint(r.x + r.width - 2 - 5 - arrowSize, centerY + arrowSize);
            g2.fillPolygon(rightArrow);
        }

        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Track color (background)
        g2.setColor(Style.FLASHCARD_BACKGROUND_COLOR);
        g2.fillRect(r.x, r.y, r.width, r.height);

        // Draw 4px black border at the top of the track
        g2.setColor(Color.BLACK);
        g2.fillRect(r.x, r.y, r.width, 4);

        g2.dispose();
    }
}
