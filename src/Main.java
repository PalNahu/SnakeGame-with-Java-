import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        int ancho = 800;
        int alto = ancho;
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Snake Game");
            ventana.setVisible(true);
            ventana.setSize(800, 800);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);
            ventana.setLocationRelativeTo(null);
            ventana.setBackground(Color.black);

            SnakeGame snakeGame = new SnakeGame(ancho, alto);
            ventana.add(snakeGame);
            snakeGame.requestFocus();
            ventana.pack();
        });
    }
}





