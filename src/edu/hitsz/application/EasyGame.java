package edu.hitsz.application;

import edu.hitsz.BGM.BGMPath;
import edu.hitsz.BGM.MusicThread;
import edu.hitsz.aircraft.AbstractAircraft;

import java.util.List;

public class EasyGame extends Game{
    public EasyGame(int mode) {
        super(mode);
    }

    @Override
    public List<AbstractAircraft> createBossAircraft(List<AbstractAircraft> enemyAircrafts, int score) {
        return enemyAircrafts;
    }

    @Override
    public int increaseEnemyMaximum(int enemyMaxNumber) {
        return enemyMaxNumber;
    }

    @Override
    public double increaseEliteRate(double eliteEnemyRate) {
        return eliteEnemyRate;
    }

    @Override
    public List<AbstractAircraft> createEliteAircraft(List<AbstractAircraft> enemyAircrafts, int round) {
        return enemyAircrafts;
    }

    @Override
    public List<AbstractAircraft> createMobAircraft(List<AbstractAircraft> enemyAircrafts, int round) {
        enemyAircrafts.add(mobEnemyFactory.createEnemyAircraft(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                6,
                30
        ));
        return enemyAircrafts;
    }
    @Override
    public int decreaseDuration(int cycleDuration) {
        return cycleDuration;
    }
}
