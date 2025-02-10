import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Classe que gerencia a lógica do jogo e a renderização gráfica.
 */
public class GamePanel extends JPanel implements ActionListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 400;
    private Timer gameTimer;
    private Background background;
    private static Player player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<PowerItem> powerItems;
    private int score;
    private int powerPoints;
    private int lives = 3;
    private boolean gameOver = false;

    /**
     * Construtor do GamePanel, inicializando os elementos do jogo.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(new PlayerKeyListener());

        background = new Background();
        player = new Player();
        obstacles = new ArrayList<>();
        powerItems = new ArrayList<>();
        gameTimer = new Timer(20, this);
    }

    /**
     * Retorna o jogador para acesso global.
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Inicia o jogo.
     */
    public void startGame() {
        gameTimer.start();
    }

    /**
     * Atualiza a lógica do jogo a cada frame.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            background.update();
            player.update();
            updateObstacles();
            updatePowerItems();
            spawnObstacles();
            spawnPowerItems();
            checkCollisions();
            repaint();
        }
    }

    /**
     * Atualiza a posição dos obstáculos existentes.
     */
    private void updateObstacles() {
        for (Obstacle obs : obstacles) {
            obs.update();
        }
    }

    /**
     * Atualiza a posição dos PowerItems existentes.
     */
    private void updatePowerItems() {
        for (PowerItem pow : powerItems) {
            pow.update();
        }
    }

    /**
     * Gera obstáculos aleatoriamente.
     */
    private void spawnObstacles() {
        if (new Random().nextInt(100) < 2) { 
            obstacles.add(new Obstacle(WIDTH));
        }
    }

    /**
     * Gera power-ups aleatoriamente.
     */
    private void spawnPowerItems() {
        if (new Random().nextInt(100) < 1) { 
            powerItems.add(new PowerItem(WIDTH));
        }
    }

    /**
     * Verifica colisões entre o player e os obstáculos ou power-ups.
     */
    private void checkCollisions() {
        Iterator<Obstacle> itObs = obstacles.iterator();
        while (itObs.hasNext()) {
            Obstacle obs = itObs.next();
            if (player.getBounds().intersects(obs.getBounds())) {
                lives--;
                itObs.remove();
                SoundManager.play("Player_perde_Vida.mp3");
                if (lives <= 0) {
                    gameOver = true;
                    SoundManager.play("Game_Over.mp3");
                }
            }
        }

        Iterator<PowerItem> itPow = powerItems.iterator();
        while (itPow.hasNext()) {
            PowerItem pow = itPow.next();
            if (player.getBounds().intersects(pow.getBounds())) {
                powerPoints += 5;
                score += 10; 
                SoundManager.play("Pegar_PowerItem.mp3");
                itPow.remove();
                
                if (powerPoints >= 20) {
                    gameOver = true;
                    SoundManager.play("Vitoria.mp3");
                }
            }
        }
    }

    /**
     * Renderiza os elementos gráficos do jogo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);
        player.draw(g);
        
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
        
        for (PowerItem pow : powerItems) {
            pow.draw(g);
        }

        // Exibe informações do jogo
        g.setColor(Color.WHITE);
        g.drawString("Vidas: " + lives, 10, 20);
        g.drawString("Poder: " + powerPoints, 10, 40);
        g.drawString("Pontuação: " + score, 10, 60);
    }
}
