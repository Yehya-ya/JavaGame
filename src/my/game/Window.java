package my.game;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

/**
 * @author Yahya-YA
 */
public class Window extends JFrame {

    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public Window() {
        this.setTitle("My Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setContentPane(new GamePanel(1920, 1080));
        device.setFullScreenWindow(this);
        this.setVisible(true);
    }
}
