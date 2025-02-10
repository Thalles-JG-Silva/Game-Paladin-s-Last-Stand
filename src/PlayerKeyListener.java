import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Classe responsável por capturar os eventos de teclado e controlar o jogador.
 */
class PlayerKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            GamePanel.getPlayer().jump();  // Acessa o jogador e executa o pulo
        }
    }
}
