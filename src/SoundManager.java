import javax.sound.sampled.*;
import java.io.File;

/**
 * Classe para gerenciar efeitos sonoros e músicas.
 */
public class SoundManager {
    public static void play(String soundFile) {
        try {
            File file = new File("assets/Musicas/" + soundFile);
            if (!file.exists()) return;

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
