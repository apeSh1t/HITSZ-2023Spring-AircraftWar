package edu.hitsz.application;

import edu.hitsz.BGM.BGMPath;
import edu.hitsz.BGM.MusicThread;
import edu.hitsz.aircraft.AbstractAircraft;

import java.util.List;

public class NormalGame extends Game{
    public NormalGame(int mode) {
        super(mode);
    }

    @Override
    public List<AbstractAircraft> createBossAircraft(List<AbstractAircraft> enemyAircrafts, int score) {
        enemyAircrafts.add( bossEnemyFactory.createEnemyAircraft(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                3,
                0,
                390
        ));
        bossMusicThread = new MusicThread(BGMPath.BOSS_BGM_PATH, true, isMusicOFF);
        bossMusicThread.start();
        mainGameMusicThread.setMusicInterruptFlag(true);
        return enemyAircrafts;
    }

    @Override
    public int increaseEnemyMaximum(int enemyMaxNumber) {
        return enemyMaxNumber+1;
    }

    @Override
    public double increaseEliteRate(double eliteEnemyRate) {
        if (eliteEnemyRate<=0.5){
            return eliteEnemyRate+0.05;
        }
        else{
            return eliteEnemyRate;
        }
    }

    @Override
    public List<AbstractAircraft> createEliteAircraft(List<AbstractAircraft> enemyAircrafts, int round) {
        enemyAircrafts.add(eliteEnemyFactory.createEnemyAircraft(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                5+round,
                60
        ));
        return enemyAircrafts;
    }

    @Override
    public List<AbstractAircraft> createMobAircraft(List<AbstractAircraft> enemyAircrafts, int round) {
        enemyAircrafts.add(mobEnemyFactory.createEnemyAircraft(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                6+round,
                30
        ));
        return enemyAircrafts;
    }

    @Override
    public int decreaseDuration(int cycleDuration) {
        if (cycleDuration>=100){
            return cycleDuration-20;
        }
        else{
            return 100;
        }
    }
}
