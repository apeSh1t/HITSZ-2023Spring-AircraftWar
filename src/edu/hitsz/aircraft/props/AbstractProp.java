package edu.hitsz.aircraft.props;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

public abstract class AbstractProp extends AbstractFlyingObject {
    //道具类型1：血包；2：子弹；3：炸弹
    private final int category;
    public int getCategory(){
        return category;
    }
    //血包治疗量
    private int increase = 30;
    public int getIncrease() {
        return increase;
    }
    public AbstractProp(int locationX, int locationY, int speedX, double speedY, int category) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = 0;
        this.speedY = speedY;
        this.category = category;
    }

    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT) {
            // 向下飞行出界
            vanish();
        } else if (locationY <= 0) {
            // 向上飞行出界
            vanish();
        }
    }


}