package my.game.gsm;

import java.awt.Graphics2D;
import my.game.inputs.KeyHandler;
import my.game.inputs.MouseHandler;

/**
 *
 * @author Yahya-YA
 */
public abstract class GameState {

    final GameStatesManager gsm;

    public GameState(GameStatesManager newGsm) {
        this.gsm = newGsm;
    }

    abstract void update();

    abstract void input(KeyHandler keyH, MouseHandler mouseH);

    abstract void rander(Graphics2D g);

}
