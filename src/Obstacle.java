import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa os obstáculos do jogo.
 */
public class Obstacle {
    private int x, y, width, height;
    private Image[] obstacleFrames;
    private int frameIndex;
    private int frameDelay;

    /**
     * Construtor do obstáculo.
     * @param x Posição inicial no eixo X.
     */
    public Obstacle(int x) {
        this.x = x;
        this.y = 300;  // 🔥 Ajustado para alinhar com o chão
        this.width = 50;
        this.height = 60;

        // 🔥 Carregar frames de animação dos obstáculos
        obstacleFrames = new Image[10];
        for (int i = 0; i < 10; i++) {
            obstacleFrames[i] = new ImageIcon("assets/Imagens/Obstacle/male/Walk_0" + (i + 1) + ".png").getImage();
        }

        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Atualiza a posição e animação do obstáculo.
     */
    public void update() {
        x -= 5;

        // 🔥 Atualizar animação
        frameDelay++;
        if (frameDelay > 5) {
            frameIndex = (frameIndex + 1) % 10;
            frameDelay = 0;
        }
    }

    /**
     * Renderiza o obstáculo na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(obstacleFrames[frameIndex], x, y, width, height, null);
    }

    /**
     * Retorna a hitbox do obstáculo.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
