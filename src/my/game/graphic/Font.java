package my.game.graphic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Yahya-YA
 */
public class Font {

    private BufferedImage fontSheet = null;
    private BufferedImage[][] FontArray;

    private int w, h;
    private int wLetter, hLetter;

    public Font(String file, int w, int h) {
        this.w = w;
        this.h = h;
        fontSheet = loadFont(file);

        wLetter = fontSheet.getWidth() / w;
        hLetter = fontSheet.getHeight() / h;

        loadFontArrey();
    }

    private BufferedImage getLetter(char letter) {
        int value = letter - ' ' + 0;
        int x = value % wLetter;
        int y = value / wLetter;

        return FontArray[x][y];
    }

    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (IOException e) {
            System.out.println("Font Loading....:: " + file);
        }

        return sprite;
    }

    private void loadFontArrey() {
        FontArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {
                FontArray[x][y] = setLetter(x, y);
            }
        }
    }

    private BufferedImage setLetter(int x, int y) {
        return this.fontSheet.getSubimage(x * w, y * h, w, h);
    }

    public void drawString(Graphics2D g, String text, int xpos, int ypos, int size, float offset) {
        int length = text.length();
        xpos = (int) (xpos - ((length * (size + offset)) / 2));
        ypos = ypos - ((size) / 2);

        for (int i = 0; i < length; i++) {
            char temp = text.charAt(i);
            BufferedImage img = getLetter(temp);
            int x = (int) (xpos + (i * size) + (i * offset));

            g.drawImage(img, x, ypos, size, size, null);
        }
    }
}
