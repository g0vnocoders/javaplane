package javaplane.Graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.Position;

public class shit extends JPanel implements Runnable {

    private int screenWidth = 500;
    private int screenHeight = 300;

    private boolean isPaused = false;
    private boolean isGameOver = false;

    private int playToPoints = 10;


    private Timer gameThread;

    public shit() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        requestFocus();
    }

    public void addNotify() {
        super.addNotify();
        startGame();
    }

    private void startGame() {
        gameThread = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGame();
                gameOverCheck();
                if (isGameOver) {
                    repaint();
                    return;
                }
                if (!isPaused) {
                    if (!gameOverCheck()) {
                        updateGame();
                    }
                }
                repaint();
            }
        });
        gameThread.start();
    }

    private boolean gameOverCheck() {

        return false;
    }

    private void updateGame() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, screenWidth + 20, screenHeight + 20);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(screenWidth / 2, 0, screenWidth / 2, screenHeight);
        g2d.setFont(new Font("serif", Font.BOLD, 32));
        g2d.drawString(100+ "", screenWidth / 2 - 40, screenHeight - 20);
        g2d.drawString(200 + "", screenWidth / 2 + 20, screenHeight - 20);
        g2d.draw3DRect(1, 20, 100, 30, true);



        g2d.dispose();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }
}
