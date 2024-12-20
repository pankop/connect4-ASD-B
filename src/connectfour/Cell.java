package connectfour;
import java.awt.*;

/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #12
 * 1 - 5026231082 - Naufal Zaky Nugraha
 * 2 - 5026231035 - Aldani Prasetyo
 * 3 - 5026231183 - Astrid Meilendra
 */
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
        
        // Draw the circle outline for empty cells
        g2d.setColor(Color.WHITE);
        g2d.fillOval(x, y, DISC_SIZE, DISC_SIZE);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawOval(x, y, DISC_SIZE, DISC_SIZE);
        
        // Fill with player color if occupied
        if (content == GamePiece.RED) {
            g2d.setColor(ConnectFour.COLOR_PLAYER1);
            g2d.fillOval(x, y, DISC_SIZE, DISC_SIZE);
        } else if (content == GamePiece.YELLOW) {
            g2d.setColor(ConnectFour.COLOR_PLAYER2);
            g2d.fillOval(x, y, DISC_SIZE, DISC_SIZE);
        }
    }
}
