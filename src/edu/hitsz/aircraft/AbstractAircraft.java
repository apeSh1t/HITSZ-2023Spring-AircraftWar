package edu.hitsz.aircraft;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.Strategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;

    private Strategy strategy;

    public AbstractAircraft(int locationX, int locationY, int speedX, double speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }

    public List<BaseBullet> executeStrategy(int LocationX, int LocationY, int SpeedX, double SpeedY, int direction, int power, int faction){
        return strategy.shootOperation(LocationX, LocationY, SpeedX, SpeedY, direction, power, faction);
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }


    /**
     * 飞机射击方法，可射击对象必须实现
     * @return
     *  可射击对象需实现，返回子弹
     *  非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();
    public abstract List<AbstractProp> drop();

}


