import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa os itens de poder coletáveis pelo jogador.
 */
public class PowerItem {
    private int x, y, width, height;
    private Image powerItemImage;

    /**
     * Construtor do PowerItem.
     * @param x Posição inicial no eixo X.
     */
    public PowerItem(int x) {
        this.x = x;
        this.y = 250;  // 🔥 Ajustado para não ficar muito alto ou desaparecer
        this.width = 30;
        this.height = 30;
        powerItemImage = new ImageIcon("assets/Imagens/PowerItem/Power_Item_01.png").getImage();
    }

    /**
     * Atualiza a posição do PowerItem (movendo para a esquerda).
     */
    public void update() {
        x -= 3;
    }

    /**
     * Renderiza o PowerItem na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(powerItemImage, x, y, width, height, null);
    }

    /**
     * Retorna a hitbox do item.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
