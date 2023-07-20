package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;

public class BossEnemyFactory implements EnemyFactory{
    @Override
    public AbstractAircraft createEnemyAircraft(int locationX, int locationY, int speedX, double speedY, int hp) {
        return new BossEnemy(locationX, locationY, speedX, speedY, hp);
    }
}
