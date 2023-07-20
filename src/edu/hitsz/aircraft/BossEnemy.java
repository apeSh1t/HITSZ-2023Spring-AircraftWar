package edu.hitsz.aircraft;

import edu.hitsz.aircraft.factory.BloodPropFactory;
import edu.hitsz.aircraft.factory.BombPropFactory;
import edu.hitsz.aircraft.factory.BulletPropFactory;
import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.BombSubscriber;
import edu.hitsz.strategy.OperationScatterShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BossEnemy extends AbstractAircraft implements BombSubscriber {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private final int shootNum = 3;

    /**
     * 子弹伤害
     */
    private final int power = 30;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private final int direction = 1;

    public BossEnemy(int locationX, int locationY, int speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public List<BaseBullet> shoot() {
        this.setStrategy(new OperationScatterShoot());
        return this.executeStrategy(locationX, locationY, speedX, speedY, direction, power, 1);
    }

    @Override
    public List<AbstractProp> drop() {
        Random r = new Random();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        double speedY =  direction * 15;

        List<AbstractProp> props = new LinkedList<>();
        BloodPropFactory bloodPropFactory = new BloodPropFactory();
        BulletPropFactory bulletPropFactory = new BulletPropFactory();
        BombPropFactory bombPropFactory = new BombPropFactory();

        for (int i = 0; i < 3; i++) {
            int randNum = r.nextInt(100);
            if (randNum <= 33) {
                props.add(bloodPropFactory.createProp(x + (i * 2 - 2)*40, y, speedX, speedY / 2, 1));
            } else if (randNum >= 34 && randNum <= 66) {
                props.add(bulletPropFactory.createProp(x + (i * 2 - 2)*40, y, speedX, speedY / 2, 2));
            } else if (randNum >= 67) {
                props.add(bombPropFactory.createProp(x + (i * 2 - 2)*40, y, speedX, speedY / 2, 3));
            }
        }
        return props;
    }

    @Override
    public int update(int score) {
        this.hp -= 60;
        if (hp<=0){
            return score+10;
        }
        return score;
    }
}
