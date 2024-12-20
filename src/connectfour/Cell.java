/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #12
 * 1 - 5026231082 - Naufal Zaky Nugraha
 * 2 - 5026231035 - Aldani Prasetyo
 * 3 - 5026231183 - Astrid Meilendra
 */

package connectfour;
import java.awt.*;
import java.awt.geom.Point2D;


public class Cell {
    public static final int SIZE = 100; // cell width/height (square)
    public static final int PADDING = SIZE / 10;
    public static final int DISC_SIZE = SIZE - PADDING * 2;

    GamePiece content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = GamePiece.EMPTY;
    }

    public void newGame() {
        content = GamePiece.EMPTY;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = col * SIZE + PADDING;
        int y = row * SIZE + PADDING;

        // Draw the circle outline for empty cells with a subtle shadow
        g2d.setColor(new Color(0, 0, 0, 30));
        g2d.fillOval(x + 2, y + 2, DISC_SIZE, DISC_SIZE);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, DISC_SIZE, DISC_SIZE);
        g2d.setColor(new Color(30, 144, 255)); // Dodger Blue
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x, y, DISC_SIZE, DISC_SIZE);

        // Fill with player color if occupied
        if (content != GamePiece.EMPTY) {
            // Create gradients for 3D effect
            Color baseColor = (content == GamePiece.RED) ? ConnectFour.COLOR_PLAYER1 : ConnectFour.COLOR_PLAYER2;
            Color lighterColor = getLighterColor(baseColor);
            Color darkerColor = getDarkerColor(baseColor);

            // Create radial gradient for main disc
            RadialGradientPaint gradient = new RadialGradientPaint(
                    new Point2D.Float(x + DISC_SIZE/2, y + DISC_SIZE/2),
                    DISC_SIZE/2,
                    new float[]{0.0f, 0.8f, 1.0f},
                    new Color[]{lighterColor, baseColor, darkerColor}
            );

            // Draw main disc with gradient
            g2d.setPaint(gradient);
            g2d.fillOval(x, y, DISC_SIZE, DISC_SIZE);

            // Add highlight
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.fillOval(x + DISC_SIZE/4, y + DISC_SIZE/4, DISC_SIZE/6, DISC_SIZE/6);

            // Add outline
            g2d.setColor(darkerColor);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x, y, DISC_SIZE, DISC_SIZE);
        }
    }

    private Color getLighterColor(Color base) {
        float[] hsb = Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), null);
        return Color.getHSBColor(hsb[0], Math.max(0.0f, hsb[1] - 0.1f), Math.min(1.0f, hsb[2] + 0.2f));
    }

    private Color getDarkerColor(Color base) {
        float[] hsb = Color.RGBtoHSB(base.getRed(), base.getGreen(), base.getBlue(), null);
        return Color.getHSBColor(hsb[0], Math.min(1.0f, hsb[1] + 0.1f), Math.max(0.0f, hsb[2] - 0.2f));
    }
}