package my.game.Level;

import java.awt.Graphics2D;
import my.game.Entity.Player;
import my.game.GamePanel;
import my.game.graphic.Font;
import my.game.graphic.Sprite;

/**
 * @author Yahya-YA
 */
public class Level {

    private final Stage stages;
    private final Font font;
    private final Player player;
    private int enemynum;
    private int level;
    private Type type;
    private int counter;
    private int introcounter;
    private boolean intro;
    private boolean Finieshed;
    private boolean next;

    public Level(Sprite enemySprite, Player player, Font font) {
        this.font = font;
        this.introcounter = 0;
        this.intro = true;
        this.type = Type.moveBy;
        this.counter = 0;
        this.next = false;
        this.level = 1;
        this.Finieshed = false;
        this.enemynum = 24;
        this.stages = new Stage(enemySprite, new Sprite("move/coin.png", 17, 18), new Sprite("move/smoke.png", 16, 16));
        this.player = player;

    }

    Level(Sprite enemySprite, Player player, int level, Type type, Font font) {
        this.font = font;
        this.introcounter = 0;
        this.intro = true;
        this.type = type;
        this.counter = 0;
        this.next = false;
        this.level = level;
        this.Finieshed = false;
        this.enemynum = 18 + level * 6;
        this.stages = new Stage(enemySprite, new Sprite("move/coin.png", 17, 18), new Sprite("move/smoke.png", 16, 16));
        this.player = player;
    }

    public void setFinieshed(boolean Finieshed) {
        this.Finieshed = Finieshed;
    }

    public boolean isFinieshed() {
        return Finieshed;
    }

    public void update() {
        if (intro) {
            introcounter++;
            if (introcounter >= 60 * 6) {
                introcounter = 0;
                intro = false;
                this.stages.setNewStage(enemynum, this.type, level);
                type = type.next();
            }
        } else {
            stages.update();
            stages.collides(player);
            stages.attack(player);
            next = stages.isFinieshed();
            if (next) {
                counter++;
                if (counter == 60 * 5) {
                    intro = true;
                    introcounter = 0;
                    if (type == Type.moveBy) {
                        level++;
                        Finieshed = true;
                        enemynum += 9;
                    }
                    counter = 0;
                }
            }

        }
    }

    public void rander(Graphics2D g) {
        stages.rander(g);
        if (intro && introcounter < 60 * 4) {

            font.drawString(g, "Wave " + (type.ordinal() + 1), GamePanel.width / 2 - 50 + (int) ((100.0f * introcounter / (60.0f * 4.0f))), GamePanel.height / 2, 60, 5);
        }
    }

    int getLevel() {
        return level;
    }

    Type getType() {
        return type;
    }
}
