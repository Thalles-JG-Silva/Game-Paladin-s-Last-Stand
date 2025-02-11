import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa o poder disparado pelo jogador.
 * - Move-se para a direita.
 * - Pode ter animação própria (5 frames).
 * - Ao colidir com um obstáculo, destrói-se e destrói o obstáculo.
 */
public class Power {
    private int x, y;
    private int width, height;
    private Image[] powerFrames;
    private int frameIndex;
    private int frameDelay;

    public Power(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 40;

        // Carrega frames (Power 01 até Power 05)
        powerFrames = new Image[5];
        for (int i = 0; i < 5; i++) {
            String filePath = "assets/Imagens/Power/Power 0" + (i + 1) + ".png";
            powerFrames[i] = new ImageIcon(filePath).getImage();
        }

        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Atualiza a posição (para a direita) e animação.
     */
    public void update() {
        // Deslocamento fixo para a direita
        x += 10;

        // Animação
        frameDelay++;
        if (frameDelay > 5) {
            frameIndex = (frameIndex + 1) % powerFrames.length;
            frameDelay = 0;
        }
    }

    /**
     * Desenha o poder na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(powerFrames[frameIndex], x, y, width, height, null);
    }

    /**
     * Hitbox para colisões.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /**
     * Retorna a posição X, útil para remover caso ultrapasse a tela.
     */
    public int getX() {
        return x;
    }
}
