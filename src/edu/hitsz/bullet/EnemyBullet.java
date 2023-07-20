package edu.hitsz.bullet;

import edu.hitsz.observer.BombSubscriber;

/**
 * @Author yu
 */
public class EnemyBullet extends BaseBullet implements BombSubscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, double speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    @Override
    public int update(int score) {
        this.vanish();
        return score;
    }
}
