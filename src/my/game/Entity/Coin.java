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
public class Coin {

    private final Animation ani;
    private final Vector2f acc;
    private final Rectangle bounds;

    public Coin(Sprite sprite, Vector2f pos, Vector2f delta) {
        int w = (17 * 2) - 4;
        int h = (2 * 18) - 4;
        int x = (int) (pos.x - w / 2);
        int y = (int) (pos.y - h / 2);
        bounds = new Rectangle(x, y, w, h);
        this.acc = delta;

        ani = new Animation();
        setAnimation(sprite.getSpriteArray(0), 5);
    }

    public Rectangle getBouns() {
        return bounds;
    }

    private void setAnimation(BufferedImage[] spriteArray, int i) {
        ani.setFrame(spriteArray);
        ani.setDelay(i);
    }

    public void update() {
        ani.update();
        move();
    }

    private void move() {
        bounds.x += acc.x;
        bounds.y += acc.y;
    }

    public void rander(Graphics2D g) {
        g.drawImage(ani.getImage(), bounds.x - 2, bounds.y - 2, bounds.width + 4, bounds.height + 4, null);
    }

    public boolean delete() {
        return bounds.y + bounds.height < 0;
    }
}
