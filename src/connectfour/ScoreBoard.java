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

import javax.swing.*;
import java.awt.*;

public class ScoreBoard extends JPanel {
    private int redScore;
    private int yellowScore;
    private final JLabel redScoreLabel;
    private final JLabel yellowScoreLabel;
    private final JButton resetButton;
    private static final Font SCORE_FONT = new Font("OCR A Extended", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("OCR A Extended", Font.PLAIN, 12);
    private static final Color BG_COLOR = new Color(216, 216, 216);
    private static final int VERTICAL_PADDING = 5;
    private static final int HORIZONTAL_PADDING = 20;

    public ScoreBoard() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING)
        ));

        redScore = 0;
        yellowScore = 0;

        // Create score panel
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, HORIZONTAL_PADDING, 0));
        scorePanel.setBackground(BG_COLOR);

        redScoreLabel = createScoreLabel("Red: 0", Color.RED);
        yellowScoreLabel = createScoreLabel("Yellow: 0", new Color(184, 134, 11));

        scorePanel.add(redScoreLabel);
        scorePanel.add(Box.createHorizontalStrut(10)); // Add spacing between labels
        scorePanel.add(new JSeparator(JSeparator.VERTICAL) {
            {
                setPreferredSize(new Dimension(1, 20));
            }
        });
        scorePanel.add(Box.createHorizontalStrut(10)); // Add spacing between labels
        scorePanel.add(yellowScoreLabel);

        // Create reset button
        resetButton = new JButton("Reset");
        resetButton.setFont(BUTTON_FONT);
        resetButton.setFocusPainted(false);
        resetButton.setMargin(new Insets(2, 8, 2, 8));
        resetButton.addActionListener(e -> resetScores());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.add(resetButton);

        // Add components to the panel
        add(scorePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        setPreferredSize(new Dimension(Board.CANVAS_WIDTH, 35));
    }

    private JLabel createScoreLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(SCORE_FONT);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        return label;
    }

    public void incrementRedScore() {
        redScore++;
        updateScoreLabels();
    }

    public void incrementYellowScore() {
        yellowScore++;
        updateScoreLabels();
    }

    public void resetScores() {
        redScore = 0;
        yellowScore = 0;
        updateScoreLabels();
    }

    private void updateScoreLabels() {
        redScoreLabel.setText("Red: " + redScore);
        yellowScoreLabel.setText("Yellow: " + yellowScore);
    }
}
