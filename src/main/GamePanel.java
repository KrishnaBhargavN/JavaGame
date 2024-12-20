package main;

import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // scaling it as 16x16 is small in most modern computers

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // total of 16 tiles in one row
    final int maxScreenRow = 12; // total of 12 tiles in one col
    final int screenWidth = tileSize * maxScreenCol; // width -> 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // height -> 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);
    // set players default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

//    @Override
//    public void run() {
//
//        long drawInterval = 1000000000/FPS;
//        long nextDrawTime = System.nanoTime() + drawInterval;
//
//        while(gameThread != null) {
//
//            update();
//
//            repaint();
//
//
//            try {
//                long remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep(remainingTime);
//
//                nextDrawTime += drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime-lastTime);
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS : " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }
}