import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa as moedas coletáveis no jogo.
 * Agora com 5 frames de animação.
 */
public class Moeda {
    private int x, y, width, height;
    private Image[] moedaFrames;
    private int frameIndex;
    private int frameDelay;

    /**
     * Construtor.
     * @param x Posição inicial X da moeda (geralmente fora da tela, à direita)
     */
    public Moeda(int x) {
        this.x = x;
        this.y = 270;   // Ajuste conforme necessidade
        this.width = 20;
        this.height = 20;

        // Carrega os 5 frames da moeda (Moeda 01.png até Moeda 05.png)
        moedaFrames = new Image[5];
        for (int i = 0; i < 5; i++) {
            String filePath = "assets/Imagens/Moedas/Moeda 0" + (i + 1) + ".png";
            moedaFrames[i] = new ImageIcon(filePath).getImage();
        }

        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Atualiza a posição e animação da moeda.
     */
    public void update() {
        // Movimento para a esquerda
        x -= 3;

        // Controle da animação
        frameDelay++;
        if (frameDelay > 8) {
            frameIndex = (frameIndex + 1) % moedaFrames.length;
            frameDelay = 0;
        }
    }

    /**
     * Desenha a moeda na tela, exibindo o frame atual.
     * @param g Objeto Graphics para desenhar
     */
    public void draw(Graphics g) {
        g.drawImage(moedaFrames[frameIndex], x, y, width, height, null);
    }

    /**
     * Retorna a área de colisão (retângulo).
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
}
