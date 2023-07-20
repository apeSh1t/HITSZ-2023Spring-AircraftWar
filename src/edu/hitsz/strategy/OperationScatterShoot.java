package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class OperationScatterShoot implements Strategy{
    @Override
    public List<BaseBullet> shootOperation(int LocationX, int LocationY, int SpeedX, double SpeedY, int direction, int power, int faction) {
        List<BaseBullet> res = new LinkedList<>();
        int x = LocationX;
        int y = LocationY + direction*2;
        double speedY = SpeedY + direction*10;
        BaseBullet bullet;
        int shootNum = 3;
        for(int i = 0; i< shootNum; i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if (faction == 0){
                bullet = new HeroBullet(x + (i*2 - shootNum + 1)*10, y, 2*i-2, speedY, power);
            }
            else {
                bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, 2*i-2, speedY, power);
            }
            res.add(bullet);
        }
        return res;
    }
}
