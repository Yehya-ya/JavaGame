package my.game.gsm;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import my.game.graphic.Font;
import my.game.graphic.Sprite;

/**
 *
 * @author Yahya-YA
 */
public class Button {

    private final Rectangle r;
    private boolean foucsed;
    private boolean clickable;
    private final String text;
    private final Font font;
    private final Sprite sprite;
    private int currentFrame;
    private BufferedImage img;
    private int xoff;
    private int yoff;

    public Button(Sprite sprite, Font font, Rectangle r, String text) {
        this.yoff = 0;
        this.xoff = 0;
        this.r = r;
        this.font = font;
        this.text = text;
        this.sprite = sprite;
        clickable = true;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setFoucsed(boolean foucsed) {
        this.foucsed = foucsed;
    }

    public boolean contains(int X, int Y) {
        if (clickable) {
            return r.contains(X, Y);
        }
        return false;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame += currentFrame;
    }

    public void update() {
        if (this.clickable) {
            if (this.foucsed) {
                this.currentFrame = 1;
            } else {
                this.currentFrame = 0;
            }
        } else {
            this.currentFrame = 4;
        }
    }

    public void rander(Graphics2D g) {
        img = sprite.anImage(currentFrame, 0);
        if (img != null) {
            g.drawImage(img, r.x + xoff, r.y + yoff, r.width, r.height, null);
        }
        font.drawString(g, text, r.x + xoff + (r.width / 2), r.y + yoff + (r.height) / 2, 60, -2);
    }

    public void setoff(int x, int y) {
        this.xoff = x;
        this.yoff = y;
    }
}
