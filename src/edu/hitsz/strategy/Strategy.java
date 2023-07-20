package edu.hitsz.strategy;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface Strategy {
    /*
    faction用于指定射出的是敌机子弹还是英雄子弹 0为英雄子弹，1为敌机子弹
     */
    public List<BaseBullet> shootOperation(int LocationX, int LocationY, int SpeedX, double SpeedY, int direction, int power, int faction);
}
