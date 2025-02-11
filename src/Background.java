import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável pelo fundo animado do jogo.
 */
public class Background {
    private Image backgroundImage;
    private int x;
    private final int WIDTH = 800;   // Largura da tela
    private final int HEIGHT = 400;  // Altura da tela

    /**
     * Construtor que carrega o background.
     */
    public Background() {
        // Carrega a imagem de fundo
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
     * @param g Objeto Graphics para desenhar
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, x, 0, WIDTH, HEIGHT, null);
        g.drawImage(backgroundImage, x + WIDTH, 0, WIDTH, HEIGHT, null);
    }
}
