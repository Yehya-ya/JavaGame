package my.game.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import my.game.graphic.Animation;
import my.game.graphic.Sprite;
import my.game.inputs.Vector2f;

/**
 * @author Yahya-YA
 */
public class Smoke {

    private final Animation ani;
    private final Rectangle bounds;
    private boolean finieshed;

    public Smoke(Sprite sprite, Vector2f pos, int s) {
        s += 2;
        int w = 16 * s;
        int h = 16 * s;
        int x = (int) (pos.x - w / 2);
        int y = (int) (pos.y - h / 2);
        bounds = new Rectangle(x, y, w, h);

        ani = new Animation();
        setAnimation(sprite.getSpriteArray(0), 2);
    }

    private void setAnimation(BufferedImage[] spriteArray, int i) {
        ani.setFrame(spriteArray);
        ani.setDelay(i);
    }

    public void update() {
        ani.update();
        this.finieshed = ani.getCurrentFrame() == ani.getFrameNumber() - 1;
        if (ani.getCurrentFrame() == 3) {
            ani.setDelay(5);
        }
    }

    public void rander(Graphics2D g) {
        g.drawImage(ani.getImage(), bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public boolean isFinieshed() {
        return finieshed;
    }
}
