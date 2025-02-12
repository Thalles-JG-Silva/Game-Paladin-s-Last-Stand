import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa o jogador (Player).
 */
public class Player {

    // Campos de posição, física, etc.
    private int x, y;
    private int width, height;
    private double velocityY;
    private boolean isJumping;
    private final double GRAVITY = 0.9;
    private final double JUMP_SPEED = -14.0;

    // Animação de corrida
    private Image[] runFrames;
    private int runFrameIndex;
    private int runFrameDelay;

    // Vidas e invulnerabilidade
    private int lives;
    private boolean invulnerable;
    private long invulnerableStartTime;
    private final long INVULNERABLE_DURATION = 2000;
    private boolean isVisible;
    private int blinkCounter;

    // Quantidade de poderes disponíveis
    private int powerCount;

    // Referência ao painel do jogo
    private GamePanel gamePanel;

    /**
     * Construtor do Player que recebe o GamePanel.
     */
    public Player(GamePanel panel) {
        this.gamePanel = panel;

        // Posição inicial
        this.x = 100;
        this.y = 300;
        this.width = 50;
        this.height = 60;

        // Pulo
        this.velocityY = 0;
        this.isJumping = false;

        // Frames de corrida
        runFrames = loadRunFrames();
        runFrameIndex = 0;
        runFrameDelay = 0;

        // 3 vidas iniciais
        lives = 3;

        // Invulnerabilidade
        invulnerable = false;
        invulnerableStartTime = 0;
        isVisible = true;
        blinkCounter = 0;

        // Sem poderes no começo
        powerCount = 0;
    }

    /**
     * Carrega frames de corrida do Player
     */
    private Image[] loadRunFrames() {
        Image[] frames = new Image[10];
        for (int i = 0; i < 10; i++) {
            String filePath = "assets/Imagens/Player/Run (" + (i + 1) + ").png";
            frames[i] = new ImageIcon(filePath).getImage();
        }
        return frames;
    }

    /**
     * Atualiza a lógica do Player (gravidade, animação, invulnerabilidade)
     */
    public void update() {
        // Animação de corrida se estiver no chão
        if (!isJumping) {
            runFrameDelay++;
            if (runFrameDelay > 5) {
                runFrameIndex = (runFrameIndex + 1) % runFrames.length;
                runFrameDelay = 0;
            }
        }

        // Física de pulo
        y += velocityY;
        velocityY += GRAVITY;
        if (y > 300) {
            y = 300;
            velocityY = 0;
            isJumping = false;
        }

        // Invulnerabilidade
        if (invulnerable) {
            long elapsed = System.currentTimeMillis() - invulnerableStartTime;
            if (elapsed > INVULNERABLE_DURATION) {
                invulnerable = false;
                isVisible = true;
            } else {
                blinkCounter++;
                if (blinkCounter % 10 == 0) {
                    isVisible = !isVisible;
                }
            }
        }
    }

    /**
     * Desenha o player, piscando se estiver invulnerável
     */
    public void draw(Graphics g) {
        if (invulnerable && !isVisible) {
            return;
        }
        g.drawImage(runFrames[runFrameIndex], x, y, width, height, null);
    }

    /**
     * Inicia o pulo, se não estiver no ar
     */
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = JUMP_SPEED;
            SoundManager.play("Player Pula.wav");
        }
    }

    /**
     * Se tiver poderes (powerCount > 0), dispara um Power
     * pelo GamePanel.shootPower(...)
     */
    public void attack() {
        if (powerCount > 0) {
            // Dispara o poder à frente do Player
            int startX = x + width;
            int startY = y + height / 2;
            gamePanel.shootPower(startX, startY);

            // Diminui um poder
            powerCount--;
            SoundManager.play("Player usa Power.wav");
        }
    }

    /**
     * Sofre dano, se não estiver invulnerável
     */
    public void takeDamage() {
        if (!invulnerable) {
            lives--;
            if (lives < 0) {
                lives = 0;
            }
            invulnerable = true;
            invulnerableStartTime = System.currentTimeMillis();
            blinkCounter = 0;
            isVisible = true;
        }
    }

    /**
     * Verifica se morreu
     */
    public boolean isDead() {
        return (lives <= 0);
    }

    /**
     * Retorna se está invulnerável
     */
    public boolean isInvulnerable() {
        return invulnerable;
    }

    /**
     * Retângulo de colisão
     */
    public Rectangle getBounds() {
        int hitboxWidth = (int) (width * 0.7);
        int hitboxHeight = (int) (height * 0.8);
        int offsetX = (width - hitboxWidth) / 2;
        int offsetY = (height - hitboxHeight) / 2;
        
        return new Rectangle(x + offsetX, y + offsetY, hitboxWidth, hitboxHeight);
    }    

    /**
     * Retorna vidas
     */
    public int getLives() {
        return lives;
    }

    /**
     * Retorna quantos poderes tem
     */
    public int getPowerCount() {
        return powerCount;
    }

    /**
     * Incrementa ao coletar um PowerItem
     */
    public void incrementPowerCount() {
        powerCount++;
    }
}
