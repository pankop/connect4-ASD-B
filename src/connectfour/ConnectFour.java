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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class ConnectFour extends JPanel {
    private static final long serialVersionUID = 1L;

    // Define named constants for the drawing graphics
    public static final String TITLE = "Connect Four";
    public static final Color COLOR_BG = new Color(18, 18, 18);  // Dark background
    public static final Color COLOR_BG_STATUS = new Color(13, 71, 161);  // Dark blue status bar
    public static final Color COLOR_PLAYER1 = new Color(244, 67, 54);  // Material Red
    public static final Color COLOR_PLAYER2 = new Color(255, 193, 7);  // Material Amber
    public static final Font FONT_STATUS = new Font("Segoe UI", Font.BOLD, 16);

    // Define game objects
    private Board board;
    private GameState currentState;
    private GamePiece currentPlayer;
    private JLabel statusBar;
    private boolean isAIMode;  // true for PvAI, false for PvP
    private SoundManager soundManager;
    private ScoreBoard scoreBoard;
    private JPanel boardPanel;  // Panel to hold the game board

    /** Constructor to setup the UI and game components */
    public ConnectFour() {
        soundManager = SoundManager.getInstance();
        board = new Board();
        scoreBoard = new ScoreBoard();

        // Setup the status bar
        statusBar = new JLabel();
        statusBar.setFont(FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 25));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 12));

        // Create board panel
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                board.paint(g);
            }
        };
        boardPanel.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT));
        boardPanel.setBackground(COLOR_BG);
        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int colSelected = mouseX / Cell.SIZE;

                if (currentState == GameState.PLAYING) {
                    if (!isAIMode || currentPlayer == GamePiece.RED) {
                        handleMove(colSelected);
                    }
                } else {
                    newGame();
                }
                boardPanel.repaint();
            }
        });

        // Setup the layout
        setLayout(new BorderLayout(0, 0));
        add(scoreBoard, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // Set the total size to accommodate all components
        int totalHeight = Board.CANVAS_HEIGHT + scoreBoard.getPreferredSize().height + statusBar.getPreferredSize().height;
        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, totalHeight));
        setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 1, true));

        // Initialize game state
        selectGameMode();
        newGame();
    }

    /** Initialize the game (run once) */
    public void initGame() {
        soundManager.startBackgroundMusic();
    }

    /** Select game mode through a dialog */
    private void selectGameMode() {
        Object[] options = {"Player vs Player", "Player vs AI"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select Game Mode",
                "Connect Four",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        isAIMode = (choice == 1);
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = GamePiece.EMPTY;
            }
        }
        currentPlayer = GamePiece.RED;
        currentState = GameState.PLAYING;
        soundManager.startBackgroundMusic();
        boardPanel.repaint();
    }

    /** Handle player move and AI move if in AI mode */
    private void handleMove(int col) {
        if (col >= 0 && col < Board.COLS && currentState == GameState.PLAYING) {
            // Look for an empty cell starting from the bottom row
            for (int row = Board.ROWS - 1; row >= 0; row--) {
                if (board.cells[row][col].content == GamePiece.EMPTY) {
                    board.cells[row][col].content = currentPlayer;
                    currentState = board.stepGame(currentPlayer, row, col);
                    boardPanel.repaint();

                    if (currentState == GameState.PLAYING) {
                        currentPlayer = (currentPlayer == GamePiece.RED) ? GamePiece.YELLOW : GamePiece.RED;

                        // If it's AI's turn
                        if (isAIMode && currentPlayer == GamePiece.YELLOW) {
                            // Add small delay for better UX
                            Timer timer = new Timer(500, e -> {
                                int aiCol = AIPlayer.makeMove(board);
                                if (aiCol != -1) {
                                    // Find empty row for AI move
                                    for (int aiRow = Board.ROWS - 1; aiRow >= 0; aiRow--) {
                                        if (board.cells[aiRow][aiCol].content == GamePiece.EMPTY) {
                                            // Make AI move
                                            board.cells[aiRow][aiCol].content = GamePiece.YELLOW;
                                            currentState = board.stepGame(GamePiece.YELLOW, aiRow, aiCol);

                                            if (currentState == GameState.YELLOW_WON) {
                                                soundManager.stopBackgroundMusic();
                                                soundManager.playAIWinSound();
                                                scoreBoard.incrementYellowScore();
                                            }

                                            if (currentState == GameState.PLAYING) {
                                                currentPlayer = GamePiece.RED;
                                            }
                                            boardPanel.repaint();
                                            break;
                                        }
                                    }
                                }
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    } else {
                        soundManager.stopBackgroundMusic();
                        if (currentState == GameState.RED_WON) {
                            soundManager.playWinSound();
                            scoreBoard.incrementRedScore();
                        } else if (currentState == GameState.YELLOW_WON && !isAIMode) {
                            soundManager.playWinSound();
                            scoreBoard.incrementYellowScore();
                        }
                    }
                    updateStatusBar();
                    break;
                }
            }
        }
    }

    /** Update the status bar with the current game state */
    private void updateStatusBar() {
        if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText((currentPlayer == GamePiece.RED) ? "Red's Turn" : "Yellow's Turn");
        } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.RED_WON) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("Red Won! Click to play again.");
        } else if (currentState == GameState.YELLOW_WON) {
            statusBar.setForeground(Color.WHITE);
            statusBar.setText("Yellow Won! Click to play again.");
        }
    }

    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI code in Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.setContentPane(new ConnectFour());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}