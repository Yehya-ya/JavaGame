package my.game.Level;

import java.awt.Graphics2D;
import my.game.Entity.Player;
import my.game.GamePanel;
import my.game.graphic.Font;
import my.game.graphic.Sprite;

/**
 *
 * @author Yahya-YA
 */
public class LevelStateManager {

    private final Level level;
    private final Player player;
    private final Font font;
    private boolean finieshed;
    private boolean intro;
    private int introcounter;

    public LevelStateManager(Sprite enemySprite, Player player, Font font) {
        this.player = player;
        this.font = font;
        this.introcounter = 0;
        this.intro = true;
        this.level = new Level(enemySprite, player, font);
        this.finieshed = false;
    }

    public LevelStateManager(Sprite enemySprite, Player player, int level, Type type, Font font) {
        this.player = player;
        this.font = font;
        this.introcounter = 0;
        this.intro = true;
        this.level = new Level(enemySprite, player, level, type, font);
        this.finieshed = false;
    }

    public boolean isFinieshed() {
        return finieshed;
    }

    public void update() {
        if (this.intro) {
            this.introcounter++;
            if (this.introcounter > 60 * 3) {
                this.introcounter = 0;
                this.intro = false;
                this.level.setFinieshed(false);
            }
        } else {
            if (!this.finieshed) {
                this.level.update();
                this.finieshed = player.isKilled();

                if (this.level.isFinieshed()) {
                    this.intro = true;
                    this.introcounter = 0;

                    this.finieshed = this.level.getLevel() > 3;
                }
            }
        }
    }

    public void rander(Graphics2D g) {
        if (this.intro) {
            this.font.drawString(g, "Level " + level.getLevel(), GamePanel.width / 2 - 100 + (int) (200.0 * (introcounter / (60.0 * 3.0))), GamePanel.height / 2, 80, 10);
        } else {
            this.level.rander(g);
        }

        String s = "" + player.getScore();

        String temp = "0";
        temp = temp.repeat(10 - s.length());

        font.drawString(g, "score:  " + temp + s, GamePanel.width - 200, 20, 20, 0);

    }

    public int getLevel() {
        return this.level.getLevel();
    }

    public Type getType() {
        return this.level.getType();
    }

    public int getScore() {
        return player.getScore();
    }
}
