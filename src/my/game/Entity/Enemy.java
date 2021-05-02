package my.game.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static my.game.Entity.Direction.DOWN;
import static my.game.Entity.Direction.LEFT;
import static my.game.Entity.Direction.RIGHT;
import static my.game.Entity.Direction.UP;
import my.game.GamePanel;
import my.game.Level.Type;
import my.game.graphic.Animation;
import my.game.graphic.Sprite;
import my.game.inputs.Vector2f;

/**
 *
 * @author Yahya-YA
 */
public class Enemy {

    private Sprite sprite;
    private final Vector2f pos;
    private final Animation ani;
    private boolean introAnimation;
    private final Type type;
    private int currentAnimation;
    private int health;

    private float deg;
    private float scaledeg;
    private float increasedeg;
    private float r;
    private final float enemySpeed;

    private final Vector2f c;
    private Vector2f delta;
    private Vector2f destination;
    private Vector2f temp;

    private final Rectangle bounds;
    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean hit;
    private final int j;
    private int x;

    public Enemy(Sprite sprite, Vector2f pos, int s, Type type, int j, int level) {
        this.hit = false;
        this.sprite = sprite;
        this.health = j;
        this.j = j - 1;
        this.pos = pos;
        this.destination = pos;
        this.delta = new Vector2f();
        this.c = new Vector2f();
        this.type = type;
        this.deg = 90;
        this.enemySpeed = 4 + 2 * level;
        bounds = new Rectangle((int) pos.x, (int) pos.y, s, s);

        introAnimation = true;
        ani = new Animation();
        setAnimation(0, sprite.getSpriteArray((4 * this.j)));
        ani.setDelay(10);
    }

    public Rectangle getBouns() {
        return bounds;
    }

    public int getJ() {
        return j;
    }

    public boolean isIntroAnimation() {
        return introAnimation;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setDelta(Vector2f v) {
        delta = v;
    }

    public void setDestination(Vector2f d, int s, float b) {
        this.r = (GamePanel.height - d.y) / 2 - 30 * s;
        this.c.y = d.y + this.r;
        this.c.x = d.x;

        switch (s) {
            case 1:
                this.scaledeg = 6;
                this.increasedeg = b;
                break;
            case 2:
                this.scaledeg = 4.5f;
                this.increasedeg = b;
                break;
            case 3:
                this.scaledeg = 3;
                this.increasedeg = b;
                break;
            default:
                break;
        }
        this.destination = d;
    }

    public void setDestination(Vector2f d) {
        this.destination = d;
    }

    private void setAnimation(int dir, BufferedImage[] spriteArray) {
        currentAnimation = dir;
        ani.setFrame(spriteArray);
    }

    public void moveCircle() {
        if (!introAnimation && type == Type.circle) {
            if (scaledeg == 4.5f) {
                destination.x = this.c.x - ((float) Math.cos(Math.toRadians(deg)) * this.r);
            } else {
                destination.x = this.c.x + ((float) Math.cos(Math.toRadians(deg)) * this.r);
            }

            destination.y = (float) Math.sin(Math.toRadians(deg)) * -this.r + this.c.y;

            deg += increasedeg;

            if (deg >= 360) {
                deg -= 360;
            }
        }
    }

    public void update() {
        ani.update();

        introMove();

        this.up = false;
        this.down = false;
        this.left = false;
        this.right = false;

        if (temp.x != 0 || temp.y != 0) {
            if (Math.abs(temp.x) >= Math.abs(temp.y)) {
                if (temp.x > 0) {
                    right = true;
                } else {
                    left = true;
                }
            } else {
                if (temp.y > 0) {
                    down = true;
                } else {
                    up = true;
                }
            }
        }

        if (up) {
            if (currentAnimation != UP.ordinal() || ani.getDelay() == -1) {
                setAnimation(UP.ordinal(), sprite.getSpriteArray(UP.ordinal() + (4 * j)));
            }
        } else if (down) {
            if (currentAnimation != DOWN.ordinal() || ani.getDelay() == -1) {
                setAnimation(DOWN.ordinal(), sprite.getSpriteArray(DOWN.ordinal() + (4 * j)));
            }
        } else if (left) {
            if (currentAnimation != LEFT.ordinal() || ani.getDelay() == -1) {
                setAnimation(LEFT.ordinal(), sprite.getSpriteArray(LEFT.ordinal() + (4 * j)));
            }
        } else if (right) {
            if (currentAnimation != RIGHT.ordinal() || ani.getDelay() == -1) {
                setAnimation(RIGHT.ordinal(), sprite.getSpriteArray(RIGHT.ordinal() + (4 * j)));
            }
        } else {
            ani.setCurrentFrame(0);
        }
        bounds.x = (int) pos.x;
        bounds.y = (int) pos.y;
    }

    private void introMove() {
        if (pos.distance(destination) > 0.5) {
            Vector2f v = new Vector2f(destination.x - pos.x, destination.y - pos.y);

            temp = delta.plus(v);
            delta.mag((v.getMag() / 2));

            temp.Scale(0.5);

            if (temp.getMag() > this.enemySpeed) {
                temp.mag(this.enemySpeed);
            }

            pos.x += temp.x;
            pos.y += temp.y;
        } else {
            introAnimation = false;
        }
    }

    public void rander(Graphics2D g) {

        if (hit) {
            x = 20;
            this.hit = false;
        }
        if (x > 0) {
            x--;
        }
        int off = x / 2;
        if (currentAnimation == 1 || currentAnimation == 2) {
            g.drawImage(ani.getImage(), bounds.x - bounds.width / 2 - off, (int) pos.y - bounds.height - x, bounds.width * 2 + off * 2, bounds.height * 2 + off * 2, null);
        } else {
            g.drawImage(ani.getImage(), bounds.x - bounds.width / 2 - off, (int) (bounds.y - bounds.height / 1.5) - x, bounds.width * 2 + off * 2, bounds.height * 2 + off * 2, null);
        }
    }

    public void move(Vector2f newacc) {
        destination.x += newacc.x;
        destination.y += newacc.y;
    }

    public void attack(Rectangle r) {
        Vector2f t = new Vector2f(r.x + r.width / 2 - pos.x, r.y - pos.y);
        t.Scale(10);
        destination.x = pos.x + t.x;
        destination.y = pos.y + t.y;
        this.delta.x = 0;
        this.delta.y = 0;
    }

    public boolean hit() {
        health--;
        return health <= 0;
    }
}
