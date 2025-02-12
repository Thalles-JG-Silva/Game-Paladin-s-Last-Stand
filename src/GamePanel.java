import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Painel principal do jogo, responsável pelo loop e pela lógica geral.
 */
public class GamePanel extends JPanel implements ActionListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 400;

    private Timer gameTimer;
    private Background background;
    private static Player player;

    // Estados possíveis do jogo
    public enum GameState {
        MENU,
        PLAYING,
        GAMEOVER
    }

    private GameState gameState = GameState.MENU; // Inicia no MENU

    // Listas de objetos do jogo
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Moeda> moedas;
    private ArrayList<PowerItem> powerItems;
    private ArrayList<Power> activePowers;

    private int totalMoedasColetadas;
    private Font gameFont;
    private Random random;

    private void triggerVictory() {
        // Toca a música de vitória
        SoundManager.play("Vitoria.wav");

        // Define o estado do jogo como GAMEOVER (para exibir a mensagem)
        gameState = GameState.GAMEOVER;

        // Salva o tempo atual para controlar o retorno ao menu
        gameOverStartTime = System.currentTimeMillis();

        // Define um Timer para voltar ao MENU após alguns segundos
        new Timer((int) GAMEOVER_DURATION, _ -> gameState = GameState.MENU).start();
    }

    // Momento em que o jogo entrou em GAMEOVER
    private long gameOverStartTime;
    // Quanto tempo ficar em GAMEOVER antes de voltar ao MENU (ms)
    private final long GAMEOVER_DURATION = 2000;

    /**
     * Construtor do GamePanel.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();

        // Adiciona o KeyListener (que cuida do menu e do player)
        addKeyListener(new PlayerKeyListener(this));

        background = new Background();

        // Cria o Player (só efetivamente usado quando gameState == PLAYING)
        player = new Player(this);

        // Inicializa as listas
        obstacles = new ArrayList<>();
        moedas = new ArrayList<>();
        powerItems = new ArrayList<>();
        activePowers = new ArrayList<>();

        // Timer do jogo (~50 FPS)
        gameTimer = new Timer(20, this);

        gameFont = new Font("Arial", Font.PLAIN, 14);
        random = new Random();

        totalMoedasColetadas = 0;

        // Inicia a música de fundo
        SoundManager.playLoop("Musica de Fundo.wav");

        // Iniciamos o Timer mesmo em MENU, para poder desenhar/reagir a teclas
        gameTimer.start();
    }

    /**
     * Retorna o player (estático, usado em PlayerKeyListener).
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Inicia o jogo de fato: zera variáveis, posiciona objetos, etc.
     */
    public void startGame() {
        resetGame();
        gameState = GameState.PLAYING;
    }

    /**
     * Redefine tudo para começar uma nova rodada.
     */
    public void resetGame() {
        // Cria um novo Player (para zerar vidas, poderes, etc.)
        player = new Player(this);

        // Limpa listas e repovoa
        obstacles.clear();
        moedas.clear();
        powerItems.clear();
        activePowers.clear();
        spawnInitialObjects();

        totalMoedasColetadas = 0;
    }

    /**
     * Cria alguns objetos iniciais para povoar a tela no começo.
     */
    private void spawnInitialObjects() {
        for (int i = 0; i < 3; i++) {
            addObstacle();
        }
        for (int i = 0; i < 3; i++) {
            addMoeda();
        }
        for (int i = 0; i < 2; i++) {
            addPowerItem();
        }
    }

    /**
     * Adiciona um obstáculo à lista, randomizando se é "female" ou "male"
     * e a posição X dentro de um intervalo.
     */
    private void addObstacle() {
        boolean isFemale = random.nextBoolean();
        obstacles.add(new Obstacle(WIDTH + random.nextInt(600),
                isFemale ? "female" : "male"));
    }

    /**
     * Adiciona uma moeda à lista, randomizando a posição X dentro de um intervalo.
     */
    private void addMoeda() {
        moedas.add(new Moeda(WIDTH + random.nextInt(500)));
    }

    /**
     * Adiciona um item de poder à lista, randomizando a posição X dentro de um
     * intervalo.
     */
    private void addPowerItem() {
        powerItems.add(new PowerItem(WIDTH + random.nextInt(500)));
    }

    /**
     * Método chamado pelo Timer (actionPerformed) para atualizar o jogo e repintar.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (gameState) {
            case MENU:
                // Apenas repinta para desenhar o menu
                break;
            case PLAYING:
                updateGame();
                break;
            case GAMEOVER:
                long elapsed = System.currentTimeMillis() - gameOverStartTime;
                if (elapsed >= GAMEOVER_DURATION) {
                    // Volta ao MENU após o tempo definido
                    gameState = GameState.MENU;
                }
                break;
        }
        repaint();
    }

    /**
     * Atualiza a lógica do jogo.
     */
    private void updateGame() {
        background.update();
        player.update();
        updateObstacles();
        updateMoedas();
        updatePowerItems();
        updateActivePowers();
        checkCollisions();

        if (player.isDead()) {
            // Toca som de Game Over
            SoundManager.play("Game Over.wav");
            gameOverStartTime = System.currentTimeMillis();
            gameState = GameState.GAMEOVER;
        }
    }

    private void updateObstacles() {
        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle obs = obstacles.get(i);
            obs.update();
            if (obs.getX() + obs.getWidth() < 0) {
                obstacles.remove(i);
                addObstacle();
                i--;
            }
        }
    }

    private void updateMoedas() {
        for (int i = 0; i < moedas.size(); i++) {
            Moeda moeda = moedas.get(i);
            moeda.update();
            if (moeda.getX() + moeda.getWidth() < 0) {
                moedas.remove(i);
                addMoeda();
            }
        }
    }

    private void updatePowerItems() {
        for (int i = 0; i < powerItems.size(); i++) {
            PowerItem powerItem = powerItems.get(i);
            powerItem.update();
            if (powerItem.getX() + powerItem.getWidth() < 0) {
                powerItems.remove(i);
                addPowerItem();
            }
        }
    }

    private void updateActivePowers() {
        for (int i = 0; i < activePowers.size(); i++) {
            Power p = activePowers.get(i);
            p.update();
            if (p.getX() > WIDTH) {
                activePowers.remove(i);
            }
        }
    }

    /**
     * Verifica todas as colisões (jogador vs obstáculos, moedas, itens, etc.).
     */
    private void checkCollisions() {
        // Jogador vs Obstáculos
        for (Obstacle obs : obstacles) {
            if (!player.isInvulnerable() && player.getBounds().intersects(obs.getBounds())) {
                player.takeDamage();
                SoundManager.play("Player perde Vida.wav");
                break;
            }
        }

        // Jogador vs Moedas
        for (int i = 0; i < moedas.size(); i++) {
            if (player.getBounds().intersects(moedas.get(i).getBounds())) {
                moedas.remove(i);
                totalMoedasColetadas++;
                SoundManager.play("Pegar Moedas.wav");
                addMoeda();

                // Verifica se o jogador coletou 10 moedas
                if (totalMoedasColetadas >= 10) {
                    triggerVictory();
                }
            }
        }

        // Jogador vs PowerItem
        for (int i = 0; i < powerItems.size(); i++) {
            if (player.getBounds().intersects(powerItems.get(i).getBounds())) {
                powerItems.remove(i);
                player.incrementPowerCount();
                SoundManager.play("Pegar PowerItem.wav");
                addPowerItem();
            }
        }

        // Power vs Obstáculos
        for (int i = 0; i < activePowers.size(); i++) {
            Power p = activePowers.get(i);
            Rectangle pBounds = p.getBounds();
            for (int j = 0; j < obstacles.size(); j++) {
                if (pBounds.intersects(obstacles.get(j).getBounds())) {
                    // Remove obstáculo
                    obstacles.remove(j);
                    addObstacle();
                    // Remove o poder
                    activePowers.remove(i);
                    i--;
                    break;
                }
            }
        }
    }

    /**
     * Desenha tudo, dependendo do estado do jogo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Sempre desenha o background
        background.draw(g);

        switch (gameState) {
            case MENU:
                drawMenu(g);
                break;
            case PLAYING:
                drawGame(g);
                break;
            case GAMEOVER:
                drawGameOver(g);
                break;
        }
    }

    private void drawGame(Graphics g) {
        // Player
        player.draw(g);

        // Obstáculos
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }

        // Moedas
        for (Moeda moeda : moedas) {
            moeda.draw(g);
        }

        // Itens de poder
        for (PowerItem powerItem : powerItems) {
            powerItem.draw(g);
        }

        // Poderes ativos
        for (Power p : activePowers) {
            p.draw(g);
        }

        // HUD
        g.setFont(gameFont);
        g.setColor(Color.BLACK);
        g.drawString("Vidas: " + player.getLives(), 10, 20);
        g.drawString("Moedas: " + totalMoedasColetadas, 10, 40);
        g.drawString("Poderes: " + player.getPowerCount(), 10, 60);
        g.drawString("Controles: SPACE - Pular | X - Usar Poder", 10, 80);
    }

    private void drawMenu(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.BLUE);
        String title = "PALADIN'S LAST STAND";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        g.drawString(title, (WIDTH - titleWidth) / 2, HEIGHT / 2 - 50);

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        g.setColor(Color.BLACK);
        String startMsg = "Pressione ENTER para Começar";
        String exitMsg = "Pressione S (ou ESC) para Sair";
        int msgWidth = g.getFontMetrics().stringWidth(startMsg);
        g.drawString(startMsg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
        int exitWidth = g.getFontMetrics().stringWidth(exitMsg);
        g.drawString(exitMsg, (WIDTH - exitWidth) / 2, HEIGHT / 2 + 30);
    }

    private void drawGameOver(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 30));

        if (totalMoedasColetadas >= 10) {
            g.setColor(Color.BLUE);
            String msg = "VITÓRIA!";
            int msgWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
        } else {
            g.setColor(Color.RED);
            String msg = "GAME OVER!";
            int msgWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
        }

    }

    /**
     * Chamado pelo Player para criar/disparar um novo poder.
     * 
     * @param startX Posição X inicial do poder
     * @param startY Posição Y inicial do poder
     */
    public void shootPower(int startX, int startY) {
        Power power = new Power(startX, startY);
        activePowers.add(power);
    }

    // Getters/setters de estado
    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }
}
