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
public class Board {
    // Define named constants
    public static final int ROWS = 6;  // 6x7 grid for Connect Four
    public static final int COLS = 7;
    
    // Define named constants for drawing
    public static final int CANVAS_WIDTH = Cell.SIZE * COLS;
    public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    public static final Color COLOR_GRID = Color.BLUE;  // Blue grid for Connect Four
    public static final int Y_OFFSET = 1;

    Cell[][] cells;

    public Board() {
        initGame();
    }

    public void initGame() {
        cells = new Cell[ROWS][COLS];
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();
            }
        }
    }

    public GameState stepGame(GamePiece player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Check for win conditions (4-in-a-line)
        // Check horizontally
        for (int col = 0; col <= COLS - 4; col++) {
            if (cells[selectedRow][col].content == player &&
                cells[selectedRow][col+1].content == player &&
                cells[selectedRow][col+2].content == player &&
                cells[selectedRow][col+3].content == player) {
                return (player == GamePiece.RED) ? GameState.RED_WON : GameState.YELLOW_WON;
            }
        }

        // Check vertically
        for (int row = 0; row <= ROWS - 4; row++) {
            if (cells[row][selectedCol].content == player &&
                cells[row+1][selectedCol].content == player &&
                cells[row+2][selectedCol].content == player &&
                cells[row+3][selectedCol].content == player) {
                return (player == GamePiece.RED) ? GameState.RED_WON : GameState.YELLOW_WON;
            }
        }

        // Check diagonal (top-left to bottom-right)
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = 0; col <= COLS - 4; col++) {
                if (cells[row][col].content == player &&
                    cells[row+1][col+1].content == player &&
                    cells[row+2][col+2].content == player &&
                    cells[row+3][col+3].content == player) {
                    return (player == GamePiece.RED) ? GameState.RED_WON : GameState.YELLOW_WON;
                }
            }
        }

        // Check diagonal (top-right to bottom-left)
        for (int row = 0; row <= ROWS - 4; row++) {
            for (int col = 3; col < COLS; col++) {
                if (cells[row][col].content == player &&
                    cells[row+1][col-1].content == player &&
                    cells[row+2][col-2].content == player &&
                    cells[row+3][col-3].content == player) {
                    return (player == GamePiece.RED) ? GameState.RED_WON : GameState.YELLOW_WON;
                }
            }
        }

        // Check for draw
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (cells[row][col].content == GamePiece.EMPTY) {
                    return GameState.PLAYING;
                }
            }
        }
        return GameState.DRAW;
    }

    public void paint(Graphics g) {
        // Draw the grid-lines
        g.setColor(COLOR_GRID);
        for (int row = 1; row < ROWS; ++row) {
            g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDTH_HALF,
                    CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < COLS; ++col) {
            g.fillRoundRect(Cell.SIZE * col - GRID_WIDTH_HALF, 0 + Y_OFFSET,
                    GRID_WIDTH, CANVAS_HEIGHT - 1,
                    GRID_WIDTH, GRID_WIDTH);
        }

        // Draw all the cells
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}
