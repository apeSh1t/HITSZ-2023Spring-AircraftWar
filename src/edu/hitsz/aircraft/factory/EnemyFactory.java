package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    public abstract AbstractAircraft createEnemyAircraft(int locationX, int locationY, int speedX, double speedY, int hp);
}
