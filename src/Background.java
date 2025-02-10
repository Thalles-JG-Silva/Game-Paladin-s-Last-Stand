import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável pelo fundo animado do jogo.
 */
public class Background {
    private Image backgroundImage;
    private int x;
    private final int WIDTH = 800;   // Ajustado para a largura da tela
    private final int HEIGHT = 400;  // Agora ocupa toda a tela do jogo

    /**
     * Construtor que carrega o background.
     */
    public Background() {
        backgroundImage = new ImageIcon("assets/Imagens/Background/Background_Deserto.png").getImage();
        x = 0;
    }

    /**
     * Atualiza a posição do background para criar o efeito de movimento.
     */
    public void update() {
        x -= 2;
        if (x <= -WIDTH) {
            x = 0;
        }
    }

    /**
     * Renderiza o background na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, x, 0, WIDTH, HEIGHT, null);  // 🔥 Agora cobre toda a tela
        g.drawImage(backgroundImage, x + WIDTH, 0, WIDTH, HEIGHT, null);
    }
}
