package my.game.graphic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Yahya-YA
 */
public class Sprite {

    private BufferedImage spriteSheet;
    private BufferedImage[][] spriteArray;

    private final int w;
    private final int h;
    private final int wSprite;
    private final int hSprite;

    public Sprite(String file, int w, int h) {
        this.spriteSheet = null;
        this.w = w;
        this.h = h;
        spriteSheet = loadSprite(file);

        wSprite = spriteSheet.getWidth() / w;
        hSprite = spriteSheet.getHeight() / h;
        loadSpriteArrey();
    }

    public BufferedImage[] getSpriteArray(int i) {
        return this.spriteArray[i];
    }

    private BufferedImage getSprite(int x, int y) {
        return this.spriteSheet.getSubimage(y * w, x * h, w, h);
    }

    public BufferedImage anImage(int x, int y) {
        if (x < hSprite && y < wSprite) {
            return this.spriteArray[x][y];
        } else {
            return null;
        }
    }

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            System.out.println("Sprite Loading....:: " + file);
        }

        return sprite;
    }

    private void loadSpriteArrey() {
        spriteArray = new BufferedImage[hSprite][wSprite];

        for (int x = 0; x < hSprite; x++) {
            for (int y = 0; y < wSprite; y++) {
                spriteArray[x][y] = getSprite(x, y);
            }
        }
    }
}
