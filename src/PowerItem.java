import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa os itens de poder coletáveis pelo jogador,
 * agora com animação nos 4 frames disponíveis.
 */
public class PowerItem {
    private int x, y;
    private int width, height;
    private Image[] powerItemFrames;
    private int frameIndex;
    private int frameDelay;

    /**
     * Construtor.
     * @param x Posição X inicial (geralmente fora da tela, à direita)
     */
    public PowerItem(int x) {
        this.x = x;
        this.y = 250; // Ajuste conforme a altura desejada
        this.width = 30;
        this.height = 30;

        // Carrega os 4 frames do item (Power_Item 01 ... 04)
        powerItemFrames = new Image[4];
        for (int i = 0; i < 4; i++) {
            String filePath = "assets/Imagens/PowerItem/Power_Item 0" + (i + 1) + ".png";
            powerItemFrames[i] = new ImageIcon(filePath).getImage();
        }

        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Atualiza a posição do item (movimento para a esquerda)
     * e avança a animação.
     */
    public void update() {
        // Movimento para a esquerda
        x -= 3;

        // Animação
        frameDelay++;
        if (frameDelay > 8) {
            frameIndex = (frameIndex + 1) % powerItemFrames.length;
            frameDelay = 0;
        }
    }

    /**
     * Desenha o item de poder na tela, exibindo o frame atual.
     */
    public void draw(Graphics g) {
        g.drawImage(powerItemFrames[frameIndex], x, y, width, height, null);
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
