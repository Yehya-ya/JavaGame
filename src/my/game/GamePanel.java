package my.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import my.game.inputs.KeyHandler;
import my.game.inputs.MouseHandler;
import my.game.gsm.GameStatesManager;

/**
 *
 * @author Yahya-YA
 */
public class GamePanel extends JPanel implements Runnable {

    public static int width, height;
    private Thread thread;
    private boolean running;
    private BufferedImage img;
    private BufferedImage i;
    private Graphics2D g;
    private KeyHandler keyH;
    private MouseHandler mouseH;
    private GameStatesManager gsm;

    public GamePanel(int width, int height) {
        GamePanel.width = width;
        GamePanel.height = height;
        this.setPreferredSize(new Dimension(width, height));
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void addNotify() {
        super.addNotify(); //To change body of generated methods, choose Tools | Templates.

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init() {
        running = true;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        i = null;
        try {
            i = ImageIO.read(getClass().getClassLoader().getResourceAsStream("Image/background.png"));
        } catch (IOException e) {
            System.out.println("background Loading....:: " + e);
        }

        keyH = new KeyHandler(this);
        mouseH = new MouseHandler(this);

        gsm = new GameStatesManager();

    }

    @Override
    public void run() {
        init();

        final double GAME_HZ = 60.0;
        final double timeBeforeUpdate = 1000000000 / GAME_HZ;

        final int mustUpdateBeforeRander = 5;

        double lastUpdateTime = System.nanoTime();
        double lastRanderTime;

        final double GAME_FPS = 60;
        final double totalTimeBeforeRander = 1000000000 / GAME_FPS;

        int frameCount = 0;
        int lastSecondTime = (int) lastUpdateTime / 1000000000;
        int oldFrameCount = 0;
        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while ((now - lastUpdateTime > timeBeforeUpdate) && (updateCount < mustUpdateBeforeRander)) {
                update();
                input();
                lastUpdateTime += timeBeforeUpdate;
                updateCount++;
            }
            if (now - lastUpdateTime > timeBeforeUpdate) {
                lastUpdateTime = now - timeBeforeUpdate;
            }
            input();
            rander();
            draw();
            lastRanderTime = now;
            frameCount++;

            int thisSecond = (int) lastRanderTime / 1000000000;
            if (thisSecond > lastSecondTime) {

                if (frameCount != oldFrameCount) {
                    System.out.println("New second " + thisSecond + " " + lastSecondTime);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while (now - lastRanderTime < totalTimeBeforeRander && now - lastUpdateTime < timeBeforeUpdate) {
                Thread.yield();

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Error: yielding thread");
                }
                now = System.nanoTime();
            }
        }
    }

    private void input() {
        keyH.tick();
        gsm.input(keyH, mouseH);
    }

    private void update() {
        gsm.update();
    }

    private void draw() {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();
    }

    private void rander() {
        if (g != null) {
            g.drawImage(i, 0, 0, GamePanel.width, GamePanel.height, null);
            gsm.rander(g);
        }
    }

}
