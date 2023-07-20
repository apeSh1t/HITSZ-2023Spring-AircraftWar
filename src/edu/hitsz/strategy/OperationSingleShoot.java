package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class OperationSingleShoot implements Strategy{
    @Override
    public List<BaseBullet> shootOperation(int LocationX, int LocationY, int SpeedX, double SpeedY, int direction, int power, int faction) {
        List<BaseBullet> res = new LinkedList<>();
        int x = LocationX;
        int y = LocationY + direction*2;
        int speedX = 0;
        double speedY = SpeedY + direction*10;
        BaseBullet bullet;
        if (faction == 0){
            bullet = new HeroBullet(x, y, speedX, speedY, power);
        }
        else{
            bullet = new EnemyBullet(x, y, speedX, speedY, power);
        }
        res.add(bullet);

        return res;
    }
}
