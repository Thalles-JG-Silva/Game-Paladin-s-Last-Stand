import javax.swing.*;

/**
 * Classe principal que inicializa o jogo.
 */
public class Game {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo 2D - Paladin's Last Stand");
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.startGame();
    }
}
