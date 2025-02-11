import javax.sound.sampled.*;
import java.io.File;

/**
 * Classe que gerencia os sons do jogo.
 */
public class SoundManager {
    private static Clip backgroundClip;

    /**
     * Toca um efeito sonoro curto (sem loop).
     * @param soundFile Nome do arquivo de som (deve estar em assets/Musicas/)
     */
    public static void play(String soundFile) {
        try {
            File file = new File("assets/Musicas/" + soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Toca música de fundo em loop (para faixas longas).
     * @param soundFile Nome do arquivo de som (deve estar em assets/Musicas/)
     */
    public static void playLoop(String soundFile) {
        try {
            if (backgroundClip != null && backgroundClip.isRunning()) {
                backgroundClip.stop();
            }

            File file = new File("assets/Musicas/" + soundFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
