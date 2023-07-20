package edu.hitsz.aircraft;

import edu.hitsz.aircraft.factory.BloodPropFactory;
import edu.hitsz.aircraft.factory.BombPropFactory;
import edu.hitsz.aircraft.factory.BulletPropFactory;
import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.BombSubscriber;
import edu.hitsz.strategy.OperationSingleShoot;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 普通敌机
 * 不可射击
 *
 * @author yu
 */
public class EliteEnemy extends AbstractAircraft implements BombSubscriber {

    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private int direction = 1;




    public EliteEnemy(int locationX, int locationY, int speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        this.setStrategy(new OperationSingleShoot());
        return this.executeStrategy(locationX, locationY, speedX, speedY, direction, power, 1);
    }


    /**
     * 爆装备
     *
     * @return 三种装备之一
     */
    public List<AbstractProp> drop() {
        Random r = new Random();
        /**
         * 1-100的随机数
         */
        int randNum = r.nextInt(100);
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        double speedY = direction * 15;

        List<AbstractProp> props = new LinkedList<>();
        BloodPropFactory bloodPropFactory = new BloodPropFactory();
        BulletPropFactory bulletPropFactory = new BulletPropFactory();
        BombPropFactory bombPropFactory = new BombPropFactory();


        if (randNum <= 30) {
            props.add(bloodPropFactory.createProp(x, y, speedX, speedY / 3, 1));

        } else if (randNum >= 31 && randNum <= 60) {
            props.add(bulletPropFactory.createProp(x, y, speedX, speedY / 3, 2));

        } else if (randNum >= 61 && randNum <= 90) {
            props.add(bombPropFactory.createProp(x, y, speedX, speedY / 3, 3));
        }
        return props;
    }

    @Override
    public int update(int score) {
        this.vanish();
        return score+10;
    }
}
