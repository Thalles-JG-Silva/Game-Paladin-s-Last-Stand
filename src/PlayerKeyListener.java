import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerKeyListener extends KeyAdapter {

    private GamePanel gamePanel;

    public PlayerKeyListener(GamePanel gp) {
        this.gamePanel = gp;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Verifica em qual estado o jogo está
        GamePanel.GameState currentState = gamePanel.getGameState();

        switch (currentState) {
            case MENU:
                // No MENU, ENTER inicia o jogo, S sai
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gamePanel.startGame();
                } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                break;

            case PLAYING:
                // No jogo, SPACE = pular, X = atacar
                Player p = GamePanel.getPlayer();
                if (p == null) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        p.jump();
                        break;
                    case KeyEvent.VK_X:
                        p.attack();
                        break;
                    default:
                        break;
                }
                break;

            case GAMEOVER:
                // Durante o GAMEOVER, ignoramos entradas
                // pois voltaremos automaticamente ao menu depois de 2s
                break;
        }
    }
}
