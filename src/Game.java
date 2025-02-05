import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Game {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Desafio do Paladino");
        config.setWindowedMode(1280, 720); // Resolução aumentada para HD
        config.useVsync(true);
        new Lwjgl3Application(new GameScreen(), config);
    }
}
