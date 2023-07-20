package edu.hitsz.aircraft;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.OperationScatterShoot;
import edu.hitsz.strategy.OperationSingleShoot;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /*攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private final int power = 30;

    /**
     * 子弹射击方向 (向下发射：1，向上发射：-1)
     */
    private final int direction = -1;

    private volatile static HeroAircraft singleton = null;

    public Thread controlThread;


    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


    @Override
    public List<BaseBullet> shoot() {

        if (shootNum == 1){
            singleton.setStrategy(new OperationSingleShoot());
        }
        else{
            singleton.setStrategy(new OperationScatterShoot());
        }
        return singleton.executeStrategy(locationX, locationY, speedX, speedY, singleton.direction, singleton.power, 0);
    }

    @Override
    public List<AbstractProp> drop() {
        return null;
    }


    /**
     * @param increase 血包回复血量
     */
    public void increaseHp(int increase){
        hp += increase;
        if(hp <= 0){
            hp=0;
            vanish();
            controlThread.interrupt();
        }
        else if(hp>=maxHp){
            hp = maxHp;
        }
    }

    /**
     * 火力道具生效
     */
    public void fireSupply(){
        int seconds = 10000;
        Runnable shootControlThread = () -> {
            shootNum ++;
            try {
                Thread.sleep(seconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            shootNum--;
        };

        controlThread = new Thread(shootControlThread);
        controlThread.start();
    }

    public static HeroAircraft getSingleton(int locationX, int locationY, int speedX, int speedY, int hp){
        if(singleton == null){
            synchronized (HeroAircraft.class){
                if(singleton == null){
                    singleton = new HeroAircraft(locationX, locationY, speedX, speedY, hp);
                }
            }
        }
        return singleton;
    }

}
