package my.game.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import my.game.GamePanel;

/**
 *
 * @author Yahya-YA
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;

    public MouseHandler(GamePanel game) {
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getMouseB() {
        return mouseB;
    }

    public void unclick() {
        mouseB = -1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseB = e.getButton();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
