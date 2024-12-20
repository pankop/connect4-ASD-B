package connectfour;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private Clip backgroundMusic;
    private Clip winSound;
    private Clip aiWinSound;
    private static SoundManager instance;

    private SoundManager() {
        loadSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    private void loadSounds() {
        try {
            // Load background music
            AudioInputStream bgStream = AudioSystem.getAudioInputStream(new File("src/ingame.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(bgStream);
            // Loop continuously
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);

            // Load win sound
            AudioInputStream winStream = AudioSystem.getAudioInputStream(new File("src/Win.wav"));
            winSound = AudioSystem.getClip();
            winSound.open(winStream);

            // Load AI win sound
            AudioInputStream aiWinStream = AudioSystem.getAudioInputStream(new File("src/AiWin.wav"));
            aiWinSound = AudioSystem.getClip();
            aiWinSound.open(aiWinStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void startBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.start();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    public void playWinSound() {
        if (winSound != null) {
            winSound.setFramePosition(0);
            winSound.start();
        }
    }

    public void playAIWinSound() {
        if (aiWinSound != null) {
            aiWinSound.setFramePosition(0);
            aiWinSound.start();
        }
    }

    public void cleanup() {
        if (backgroundMusic != null) {
            backgroundMusic.close();
        }
        if (winSound != null) {
            winSound.close();
        }
        if (aiWinSound != null) {
            aiWinSound.close();
        }
    }
}