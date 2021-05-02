package my.game.graphic;

import java.awt.image.BufferedImage;

/**
 *
 * @author Yahya-YA
 */
public class Animation {

    private BufferedImage[] frames;
    private int currentFrame;
    private int frameNumber;

    private int count;
    private int delay;

    public Animation() {

    }

    public void setFrame(BufferedImage[] frames) {
        this.frames = frames;
        this.currentFrame = 0;
        this.frameNumber = frames.length;
        this.count = 0;
    }

    public void setDelay(int i) {
        this.delay = i;
    }

    public void setCurrentFrame(int i) {
        this.currentFrame = i;
    }

    public int getFrameNumber() {
        return this.frameNumber;
    }

    public void update() {
        if (delay == -1) {
            return;
        }
        count++;
        if (count == delay) {
            currentFrame++;
            count = 0;
        }
        if (currentFrame == frameNumber) {
            currentFrame = 0;
        }
    }

    public int getDelay() {
        return this.delay;
    }

    public int getCurrentFrame() {
        return this.currentFrame;
    }

    public BufferedImage getImage() {
        return frames[currentFrame];
    }

}
