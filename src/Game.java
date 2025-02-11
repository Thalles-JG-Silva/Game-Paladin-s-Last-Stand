import javax.swing.*;

/**
 * Classe principal que inicializa o jogo.
 */
public class Game {
    public static void main(String[] args) {
        // Criação da janela principal do jogo
        JFrame frame = new JFrame("Paladin's Last Stand");
        GamePanel gamePanel = new GamePanel(); // Criação do painel do jogo

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
