package my.game.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import my.game.GamePanel;

/**
 *
 * @author Yahya-YA
 */
public class KeyHandler implements KeyListener {

    public static ArrayList<Key> keys = new ArrayList<>();

    public class Key {

        public int presses, absorbs;
        public boolean down, clicked;

        public Key() {
            keys.add(this);
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
                if (pressed) {
                    presses++;
                }
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }

    public KeyHandler(GamePanel game) {
        game.addKeyListener(this);
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key shoot = new Key();
    public Key menu = new Key();
    public Key enter = new Key();
    public Key escape = new Key();

    public void releseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            shoot.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            menu.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enter.toggle(pressed);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escape.toggle(pressed);
        }

    }

    public void tick() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }
}
