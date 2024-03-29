import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
    int ancho;
    int alto;

    public void move() {
        if (colisión(cabezaSerpiente, comida)) {
            cuerpoSerpiente.add(new Tile(comida.x, comida.y));
            colocarComida();
        }

        for (int i = cuerpoSerpiente.size() - 1; i >= 0; i--) {
            Tile parteSerpiente = cuerpoSerpiente.get(i);
            if (i == 0) {
                parteSerpiente.x = cabezaSerpiente.x;
                parteSerpiente.y = cabezaSerpiente.y;
            } else {
                Tile antParteSerpiente = cuerpoSerpiente.get(i - 1);
                parteSerpiente.x = antParteSerpiente.x;
                parteSerpiente.y = antParteSerpiente.y;
            }
        }

        cabezaSerpiente.x += velocidadX;
        cabezaSerpiente.y += velocidadY;

        //Fin del juego
        for (int i = 0; i < cuerpoSerpiente.size(); i++) {
            Tile parteSerpiente = cuerpoSerpiente.get(i);
            if (colisión(parteSerpiente, cabezaSerpiente)) {
                finDelJuego = true;
            }

        }

        if(cabezaSerpiente.x*tileSize < 0 || cabezaSerpiente.y*tileSize > ancho || cabezaSerpiente.y*tileSize < 0 || cabezaSerpiente.y*tileSize > alto){
            finDelJuego = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (finDelJuego) {
            loop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && velocidadY != 1) {
            velocidadX = 0;
            velocidadY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocidadY != -1) {
            velocidadX = 0;
            velocidadY = 1;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocidadX != 1) {
            velocidadX = -1;
            velocidadY = 0;

        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocidadX != -1) {
            velocidadX = 1;
            velocidadY = 0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private static class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int tileSize = 25;
    Tile cabezaSerpiente;
    ArrayList<Tile> cuerpoSerpiente;

    Tile comida;
    Random random;

    Timer loop;
    int velocidadX;
    int velocidadY;

    boolean finDelJuego = false;

    SnakeGame(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        addKeyListener(this);
        setFocusable(true);
        setPreferredSize(new Dimension(this.ancho, this.alto));
        setBackground(Color.black);
        cabezaSerpiente = new Tile(5, 5);
        cuerpoSerpiente = new ArrayList<Tile>();
        comida = new Tile(10, 10);
        random = new Random();
        colocarComida();
        loop = new Timer(100, this);
        loop.start();
        velocidadX = 0;
        velocidadY = 0;


    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        //g.fillRect(comida.x * tileSize, comida.y * tileSize, tileSize, tileSize);
        g.fill3DRect(comida.x * tileSize, comida.y * tileSize, tileSize, tileSize, true);

        g.setColor(Color.green);
       // g.fillRect(cabezaSerpiente.x * tileSize, cabezaSerpiente.y * tileSize, tileSize, tileSize);
        g.fill3DRect(cabezaSerpiente.x * tileSize, cabezaSerpiente.y * tileSize, tileSize, tileSize, true);


        for (int i = 1; i < cuerpoSerpiente.size(); i++) {
            Tile parteSerpiente = cuerpoSerpiente.get(i);
            //g.fillRect(parteSerpiente.x * tileSize, parteSerpiente.y * tileSize, tileSize, tileSize);
            g.fill3DRect(parteSerpiente.x * tileSize, parteSerpiente.y * tileSize, tileSize, tileSize, true);
        }

        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (finDelJuego){
            g.setColor(Color.red);
            g.drawString("Fin del juego " + String.valueOf(cuerpoSerpiente.size()), tileSize - 16, tileSize);

        }else{
            g.drawString("Score " + String.valueOf(cuerpoSerpiente.size()), tileSize-16, tileSize);
        }

    }

    public void colocarComida() {
        comida.x = random.nextInt(ancho / tileSize);
        comida.y = random.nextInt(alto / tileSize);
    }

    public boolean colisión(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
}



