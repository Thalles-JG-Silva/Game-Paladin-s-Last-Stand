import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa o jogador.
 */
public class Player {
    private int x, y, width, height;
    private int velocityY;
    private boolean isJumping;
    private Image[] runFrames;
    private int frameIndex;
    private int frameDelay;

    /**
     * Construtor do jogador.
     */
    public Player() {
        this.x = 100;
        this.y = 300;  // 🔥 Ajustado para alinhar com o chão
        this.width = 50;
        this.height = 60;
        this.isJumping = false;
        this.velocityY = 0;

        // 🔥 Carregar frames da animação de corrida
        runFrames = new Image[10];
        for (int i = 0; i < 10; i++) {
            runFrames[i] = new ImageIcon("assets/Imagens/Player/Run_0" + (i + 1) + ".png").getImage();
        }

        frameIndex = 0;
        frameDelay = 0;
    }

    /**
     * Atualiza a posição e animação do jogador.
     */
    public void update() {
        if (isJumping) {
            y += velocityY;
            velocityY += 1;
            if (y >= 300) {  // 🔥 Garantir que o player não afunde
                y = 300;
                isJumping = false;
            }
        }

        // 🔥 Atualizar animação
        frameDelay++;
        if (frameDelay > 5) {
            frameIndex = (frameIndex + 1) % 10;
            frameDelay = 0;
        }
    }

    /**
     * Faz o jogador pular.
     */
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = -15;
            SoundManager.play("Player_Pula.mp3");
        }
    }

    /**
     * Renderiza o jogador na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(runFrames[frameIndex], x, y, width, height, null);
    }

    /**
     * Retorna a hitbox do jogador.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
