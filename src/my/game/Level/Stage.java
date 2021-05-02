package my.game.Level;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Random;
import my.game.Entity.Coin;
import my.game.Entity.Enemy;
import my.game.Entity.Player;
import my.game.Entity.Shoot;
import my.game.Entity.Smoke;
import my.game.GamePanel;
import my.game.graphic.Sprite;
import my.game.inputs.Vector2f;

/**
 * @author Yahya-YA
 */
class Stage {

    private final ArrayList<Enemy> enemys;
    private final ArrayList<Enemy> introEnemys;
    private final ArrayList<Enemy> attackedEnemys;
    private final ArrayList<Coin> coins;
    private final ArrayList<Smoke> smokes;
    private final Vector2f acc;
    private int enemynum;
    private Type type;
    private int counter;
    private int c;
    private int loop;
    private int delay;
    private int level;
    private boolean finishAnimation;
    private final Sprite sprite;
    private final Sprite coinSprite;
    private final Sprite smokeSprite;

    public Stage(Sprite sprite, Sprite coinSprite, Sprite smokeSprite) {
        this.smokeSprite = smokeSprite;
        this.coinSprite = coinSprite;
        this.sprite = sprite;
        this.acc = new Vector2f();
        this.enemys = new ArrayList<>();
        this.introEnemys = new ArrayList<>();
        this.attackedEnemys = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.smokes = new ArrayList<>();
    }

    public void setNewStage(int num, Type type, int level) {
        this.enemynum = num;
        this.type = type;
        this.finishAnimation = false;
        this.delay = 15;
        this.loop = 0;
        this.counter = 0;
        this.c = 45;
        this.acc.x = 1;
        this.acc.y = 0;
        this.level = level;

        enemys.clear();
        introEnemys.clear();

        switch (type) {
            case row: {
                setRowStage();
                break;
            }
            case column: {
                setColumnStage();
                break;
            }
            case circle: {
                setCircleStage();
                break;
            }
            case moveBy: {
                setMoveByStage();
                break;
            }
            default: ;
        }
    }

    private void move() {
        switch (type) {
            case row: {
                c--;
                if (c < 0) {

                    acc.x *= -1;
                    c = 90;
                }
                break;
            }
            case column: {
                c--;
                if (c < 0) {

                    acc.x *= -1;
                    c = 90;
                }
                break;
            }
            case circle: {
                acc.x = 0;
                acc.y = 0;
                break;
            }
            case moveBy: {
                acc.x = 0;
                acc.y = 0;
                break;
            }
            default:;
        }

    }

    public void update() {
        move();

        enemys.forEach((enemy) -> {
            enemy.update();
        });

        attackedEnemys.forEach((enemy) -> {
            enemy.update();
        });

        if (!finishAnimation) {
            switch (type) {
                case row: {
                    updateRowStage();
                    break;
                }
                case column: {
                    updateColumnStage();
                    break;
                }
                case circle: {
                    updateCircleStage();
                    break;
                }
                case moveBy: {
                    updateMoveByStage();
                    break;
                }
                default:;
            }
        }
        enemys.forEach((enemy) -> {
            enemy.move(acc);
        });

        if (type == Type.circle) {
            enemys.forEach((enemy) -> {
                enemy.moveCircle();
            });
        }

        coins.forEach((Coin coin) -> {
            coin.update();
        });

        smokes.forEach((Smoke s) -> {
            s.update();
        });

        for (Smoke s : this.smokes) {
            if (s.isFinieshed()) {
                smokes.remove(s);
                break;
            }
        }
    }

    public void rander(Graphics2D g) {
        enemys.forEach((Enemy e) -> {
            e.rander(g);
        });

        attackedEnemys.forEach((Enemy e) -> {
            e.rander(g);
        });

        coins.forEach((Coin coin) -> {
            coin.rander(g);
        });

        smokes.forEach((Smoke s) -> {
            s.rander(g);
        });
    }

    public void collides(Player player) {
        ArrayList<Shoot> shoots = player.getShoots();

        if (type == Type.moveBy) {
            for (Enemy e : enemys) {
                if (!e.isIntroAnimation()) {
                    enemys.remove(e);
                    break;
                }
            }
        }

        for (Enemy e : attackedEnemys) {
            if (e.getBouns().y < 0 - e.getBouns().height || e.getBouns().y > GamePanel.height) {
                attackedEnemys.remove(e);
                break;
            } else if (e.getBouns().x < 0 - e.getBouns().width || e.getBouns().x > GamePanel.width) {
                attackedEnemys.remove(e);
                break;
            }
        }

        for (Coin co : coins) {
            if (player.getBouns().intersects(co.getBouns())) {
                coins.remove(co);
                player.Score(100);
                break;
            }
        }
        
        for (Coin co : coins) {
            if (co.delete()) {
                coins.remove(co);
                break;
            }
        }

        A:
        for (Shoot s : shoots) {
            for (Enemy e : enemys) {
                if (s.getBouns().intersects(e.getBouns())) {
                    if (e.hit()) {
                        Rectangle r = e.getBouns();
                        if (random() < 0.05 * (e.getJ() + 1)) {
                            Coin temp = new Coin(this.coinSprite, new Vector2f(r.x + r.width / 2, r.y + r.height / 2), new Vector2f(0, -1));
                            coins.add(temp);
                        }
                        player.Score(e.getBouns().width * 2);
                        Smoke t = new Smoke(this.smokeSprite, new Vector2f(r.x + r.width / 2, r.y + r.height / 2), e.getJ());
                        smokes.add(t);
                        enemys.remove(e);
                    }
                    e.setHit(true);
                    shoots.remove(s);
                    break A;
                }
            }
        }

        A:
        for (Shoot s : shoots) {
            for (Enemy e : attackedEnemys) {
                if (s.getBouns().intersects(e.getBouns())) {
                    if (e.hit()) {
                        Rectangle r = e.getBouns();
                        if (random() < 0.05 * (e.getJ() + 1)) {
                            Coin temp = new Coin(this.coinSprite, new Vector2f(r.x + r.width / 2, r.y + r.height / 2), new Vector2f(0, -1));
                            coins.add(temp);
                        }
                        Smoke t = new Smoke(this.smokeSprite, new Vector2f(r.x + r.width / 2, r.y + r.height / 2), e.getJ());
                        smokes.add(t);
                        player.Score(e.getBouns().width * 2);
                        attackedEnemys.remove(e);
                    }
                    e.setHit(true);
                    shoots.remove(s);
                    break A;
                }
            }
        }

        enemys.stream().filter((e) -> (e.getBouns().intersects(player.getBouns()))).forEachOrdered((_item) -> {
            player.Kill();
        });

        attackedEnemys.stream().filter((e) -> (e.getBouns().intersects(player.getBouns()))).forEachOrdered((_item) -> {
            player.Kill();
        });
    }

    private void updateRowStage() {
        introEnemys.forEach((Enemy enemy) -> {
            enemy.move(acc);
        });

        counter++;

        if (counter == 20) {
            enemys.addAll(introEnemys.subList(loop - 1, loop));
            introEnemys.remove(loop - 1);
            loop -= 1;
            counter = 0;
            if (loop <= 0) {
                finishAnimation = true;
                introEnemys.clear();
            }
        }
    }

    private void updateColumnStage() {
        introEnemys.forEach((Enemy enemy) -> {
            enemy.move(acc);
        });

        counter++;

        if (counter == 60) {
            enemys.addAll(introEnemys.subList(0, 3));
            for (int i = 0; i < 3; i++) {
                introEnemys.remove(0);
            }
            loop += 3;
            counter = 0;
            if (loop >= enemynum) {
                finishAnimation = true;
                introEnemys.clear();
            }
        }
    }

    private void updateCircleStage() {
        counter++;
        if (counter == 20) {
            enemys.add(introEnemys.get(0));
            introEnemys.remove(0);
            loop++;
            counter = 0;
            if (loop >= enemynum) {
                finishAnimation = true;
                introEnemys.clear();
            }
        }
    }

    private void updateMoveByStage() {
        counter++;

        if (counter == delay) {
            enemys.add(introEnemys.get(0));
            introEnemys.remove(0);
            loop++;
            counter = 0;
            if (loop % 3 == 0) {
                delay = 60;
            } else {
                delay = 15;
            }

            if (loop >= enemynum) {
                finishAnimation = true;
                introEnemys.clear();
            }
        }
    }

    private void setRowStage() {
        int enemyNumRow = enemynum / 3;
        int distance = (GamePanel.width / (enemyNumRow + 3));

        for (int j = 1; j <= 3; j++) {
            for (int i = 0; i < enemyNumRow; i++) {
                Enemy temp;
                if (j % 2 == 0) {
                    temp = new Enemy(sprite, new Vector2f(GamePanel.width, GamePanel.height / 2), 64 - 8 * j, type, 4 - j, level);
                } else {
                    temp = new Enemy(sprite, new Vector2f(0 - 31, GamePanel.height / 2), 64 - 8 * j, type, 4 - j, level);
                }

                int x = (i + 2) * distance;
                int y = (GamePanel.height - GamePanel.height * j / 6);

                temp.setDelta(new Vector2f(0, -1));
                temp.setDestination(new Vector2f(x, y));

                introEnemys.add(temp);
            }
        }
        loop = enemynum;
    }

    private void setColumnStage() {
        int enemyNumRow = enemynum / 3;
        int distance = (GamePanel.width / (enemyNumRow + 3));

        for (int i = 0; i < enemyNumRow; i++) {
            for (int j = 1; j <= 3; j++) {

                Enemy temp;
                if (i < enemyNumRow / 2) {
                    temp = new Enemy(sprite, new Vector2f(-30, GamePanel.height), 64 - 8 * j, type, 4 - j, level);
                    temp.setDelta(new Vector2f(1, 3));
                } else {
                    temp = new Enemy(sprite, new Vector2f(GamePanel.width, GamePanel.height), 64 - 8 * j, type, 4 - j, level);
                    temp.setDelta(new Vector2f(-1, 3));

                }
                int x = (i + 2) * distance;
                int y = (GamePanel.height - GamePanel.height * j / 6);

                temp.setDestination(new Vector2f(x, y));

                introEnemys.add(temp);
            }
        }
    }

    private void setCircleStage() {
        int enemyNumRow = enemynum / 3 + 4;
        for (int j = 1; j <= 3; j++) {
            enemyNumRow -= 2;
            for (int i = 0; i < enemyNumRow; i++) {

                Enemy temp;
                if (j % 2 == 0) {
                    temp = new Enemy(sprite, new Vector2f(GamePanel.width / 3, GamePanel.height), 64 - 8 * j, type, 4 - j, level);
                    temp.setDelta(new Vector2f(-3, 1));
                } else {
                    temp = new Enemy(sprite, new Vector2f(GamePanel.width * 2 / 3, GamePanel.height), 62 - 8 * j, type, 4 - j, level);
                    temp.setDelta(new Vector2f(3, 1));
                }
                float b = 360.0f / (20.0f * enemyNumRow);
                temp.setDestination(new Vector2f(GamePanel.width / 2, GamePanel.height / 4 + (j * 90)), j, b);

                introEnemys.add(temp);
            }
        }
    }

    private void setMoveByStage() {
        int enemyNumRow = enemynum / 3;

        for (int j = 0; j < enemyNumRow; j++) {
            Enemy temp;
            Random r = new Random();
            int randomInteger = GamePanel.height / 4 - 60 + r.nextInt((GamePanel.height * 3 / 4));
            if (r.nextDouble() < 0.5) {
                for (int i = 0; i < 3; i++) {
                    temp = new Enemy(sprite, new Vector2f(GamePanel.width, randomInteger), 64 - 16, type, i + 1, level);
                    temp.setDestination(new Vector2f(-40, randomInteger));
                    introEnemys.add(temp);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    temp = new Enemy(sprite, new Vector2f(-31, randomInteger), 64 - 16, type, i + 1, level);
                    temp.setDestination(new Vector2f(GamePanel.width + 10, randomInteger));
                    introEnemys.add(temp);
                }
            }
        }
    }

    public boolean isFinieshed() {
        return enemys.isEmpty() & introEnemys.isEmpty();
    }

    void attack(Player player) {
        for (Enemy e : enemys) {
            if (random() < 0.001 * level) {

                e.attack(player.getBouns());
                attackedEnemys.add(e);
                enemys.remove(e);
                break;
            }
        }
    }
}
