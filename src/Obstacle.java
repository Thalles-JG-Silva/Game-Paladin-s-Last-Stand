import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Classe que representa os obstáculos no jogo.
 */
public class Obstacle {
    private int x, y, width, height;
    private Image[] frames;   // Frames de animação
    private int frameIndex;   // Índice do frame atual
    private int frameDelay;   // Contador para troca de frames

    /**
     * Construtor que recebe a posição e o tipo (female/male).
     * @param x    Posição X inicial do obstáculo
     * @param type "female" ou "male"
     */
    public Obstacle(int x, String type) {
        this.x = x;
        this.y = 300; // Ajuste conforme necessidade
        this.width = 50;
        this.height = 60;

        frames = loadFrames(type);
        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Carrega os frames de animação do obstáculo e faz o espelhamento
     * para que fiquem no lado correto.
     */
    private Image[] loadFrames(String type) {
        Image[] loadedFrames = new Image[10];
        for (int i = 0; i < 10; i++) {
            String filePath = "assets/Imagens/Obstacle/" + type + "/Walk (" + (i + 1) + ").png";
            Image original = new ImageIcon(filePath).getImage();
            loadedFrames[i] = flipImageHorizontally(original);
        }
        return loadedFrames;
    }

    /**
     * Método auxiliar para espelhar a imagem horizontalmente.
     */
    private Image flipImageHorizontally(Image src) {
        // Cria um BufferedImage
        BufferedImage bufferedSrc = new BufferedImage(
                src.getWidth(null),
                src.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = bufferedSrc.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();

        // Faz o flip usando AffineTransform
        AffineTransform transform = new AffineTransform();
        transform.scale(-1, 1);
        transform.translate(-bufferedSrc.getWidth(), 0);

        // Cria a imagem final flipada
        BufferedImage flipped = new BufferedImage(
                bufferedSrc.getWidth(),
                bufferedSrc.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2 = flipped.createGraphics();
        g2.drawImage(bufferedSrc, transform, null);
        g2.dispose();

        return flipped;
    }

    /**
     * Atualiza a posição (movendo para a esquerda) e avança a animação.
     */
    public void update() {
        x -= 5;
        frameDelay++;
        if (frameDelay > 5) {
            frameIndex = (frameIndex + 1) % 10;
            frameDelay = 0;
        }
    }

    /**
     * Renderiza o obstáculo.
     */
    public void draw(Graphics g) {
        g.drawImage(frames[frameIndex], x, y, width, height, null);
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Retorna a hitbox do obstáculo.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
