package edu.hitsz.application;

import UI.SwingControl;
import edu.hitsz.BGM.BGMPath;
import edu.hitsz.BGM.MusicThread;
import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.BossEnemyFactory;
import edu.hitsz.aircraft.factory.EliteEnemyFactory;
import edu.hitsz.aircraft.factory.MobEnemyFactory;
import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.aircraft.props.BombProp;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.observer.BombSubscriber;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {

    private static final int EASY_MODE = 0;
    private static final int NORMAL_MODE = 1;
    private static final int HARD_MODE = 2;
    private int backGroundTop = 0;

    private int bossNum = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private List<AbstractProp> props;

    private List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;

    protected MobEnemyFactory mobEnemyFactory = new MobEnemyFactory();
    protected EliteEnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    protected BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();


    /**
     * 屏幕中出现的敌机最大数量
     */
    private int enemyMaxNumber = 5;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;


    /**
     * Random类的实例化
     */
    Random r = new Random();
    /**
     * 介于1-100之间的随机整数
     */
    private int randNum = 0 ; // 生成[0,100]区间的整数

    /*
    游戏难度
     */
    private int gameMode;
    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;


    /*
    *游戏音效关闭标志
     */
    public boolean isMusicOFF;

    /*
    *主游戏音效
     */
    protected MusicThread mainGameMusicThread;

    /*
    * boss机音效
     */
    protected MusicThread bossMusicThread;

    /**
     * 精英敌机产生概率
     */
    protected double eliteEnemyRate = 0.20;

    /**
     * 定义敌机波数
     * 每30次刷新定义为一波敌机
     * 根据波数提升敌机难度
     */
    protected int playingRound = 1;

    public void setGameMode(int gameMode){
        this.gameMode = gameMode;
    }

    public Game(int mode) {
        heroAircraft = HeroAircraft.getSingleton(
                Main.WINDOW_WIDTH / 2,
                Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                0, 0, 10000);

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /*
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        this.gameMode = mode;

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        System.out.println("***************"+"第"+playingRound+"波"+"敌机***************");
        System.out.println("最大敌机数量： " + enemyMaxNumber);
        System.out.printf("精英敌机产生概率： %.2f%n",eliteEnemyRate);
        System.out.println("精英敌机速度："+ (5+playingRound));
        System.out.println("普通敌机速度："+ (6+playingRound));
        if (gameMode == 2 ){
            System.out.println("boss敌机血量："+ (390+playingRound*10));
            System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
        } else if (gameMode == 0) {
            System.out.println("无boss敌机");
            System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
        } else if (gameMode == 1) {
            System.out.println("boss敌机血量："+ 390);
            System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
        }
        System.out.println();

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                //System.out.println(time);
                randNum = r.nextInt(100);
                // 新敌机产生
                if ((enemyAircrafts.size() < enemyMaxNumber) && randNum > eliteEnemyRate*100) {
                    enemyAircrafts = createMobAircraft(enemyAircrafts, playingRound);
                }

                //精英敌机

                else if ((enemyAircrafts.size() < enemyMaxNumber) && (randNum <= eliteEnemyRate*100)) {
                    enemyAircrafts = createEliteAircraft(enemyAircrafts, playingRound);
                }


                if((score >= 300) && (score % 300 <= 10) && (bossNum == 0)){
                    enemyAircrafts = createBossAircraft(enemyAircrafts, playingRound);
                    bossNum ++;
                }

                // 飞机射出子弹
                shootAction();

                //每一波敌机之后
                if(time/(30*600) == playingRound){
                    enemyMaxNumber = increaseEnemyMaximum(enemyMaxNumber);
                    eliteEnemyRate = increaseEliteRate(eliteEnemyRate);
                    cycleDuration = decreaseDuration(cycleDuration);
                    playingRound++;
                    System.out.println("***************"+"第"+playingRound+"波"+"敌机***************");
                    System.out.println("最大敌机数量： " + enemyMaxNumber);
                    System.out.printf("精英敌机产生概率： %.2f%n",eliteEnemyRate);
                    System.out.println("精英敌机速度："+ (5+playingRound));
                    System.out.println("普通敌机速度："+ (6+playingRound));
                    if (gameMode == 2 ){
                        System.out.println("boss敌机血量："+ (390+playingRound*10));
                        System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
                    } else if (gameMode == 0) {
                        System.out.println("无boss敌机");
                        System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
                    } else if (gameMode == 1) {
                        System.out.println("boss敌机血量："+ 390);
                        System.out.println("敌机和子弹刷新频率： "+ cycleDuration+"ms/次");
                    }
                    System.out.println();
                }
            }

            // 子弹移动
            bulletsMoveAction();

            //道具移动
            propMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                new MusicThread(BGMPath.GAME_OVER_BGM_PATH, false, isMusicOFF).start();
                mainGameMusicThread.setMusicInterruptFlag(true);
                if(bossMusicThread != null) {
                    bossMusicThread.setMusicInterruptFlag(true);
                }
//                LockSupport.unpark(heroAircraft.controlThread);
//                heroAircraft.controlThread.interrupt();
//                mainGameMusicThread.interrupt();
//                bossMusicThread.interrupt();

                SwingControl.RankShow(gameMode, score);
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

        /*
        *开启游戏背景音效线程
         */
        mainGameMusicThread = new MusicThread(BGMPath.MAIN_BGM_PATH, true, isMusicOFF);
        mainGameMusicThread.start();

    }

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for(AbstractAircraft  enemyAircraft : enemyAircrafts){
            enemyBullets.addAll(enemyAircraft.shoot());
        }

        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void propMoveAction(){
        for (AbstractProp prop : props){
            prop.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof BossEnemy){
                            bossNum --;
                            bossMusicThread.setMusicInterruptFlag(true);
//                            bossMusicThread.interrupt();
                           mainGameMusicThread = new MusicThread(BGMPath.MAIN_BGM_PATH, true, isMusicOFF);
                           mainGameMusicThread.start();
//                            mainGameMusicThread.start();
//                            mainGameMusicThread.setMusicInterruptFlag(false);
                        }
                        // TODO 获得分数，产生道具补给
                        List<AbstractProp> tool=enemyAircraft.drop();
                        if(tool!=null){
                            props.addAll(tool);
                        }
                        score += 10;
                    }
                    Thread bulletHitThread = new MusicThread(BGMPath.BULLET_HIT_BGM_PATH, false, isMusicOFF);
                    bulletHitThread.start();
//                    bulletHitThread.interrupt();
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                MusicThread propCrashMusicThread = new MusicThread(BGMPath.GET_SUPPLY_BGM_PATH, false, isMusicOFF);
                //英雄机碰到道具
                if (prop.getCategory() == 1){
                    heroAircraft.increaseHp(prop.getIncrease());
                    propCrashMusicThread.start();
//                    propCrashMusicThread.interrupt();
                } else if (prop.getCategory() == 2) {
                    heroAircraft.fireSupply();
                    propCrashMusicThread.start();
//                    propCrashMusicThread.interrupt();
                    //System.out.println("FireSupply active");
                } else if (prop.getCategory() == 3 ) {
                    MusicThread bombExplosionMusicThread = new MusicThread(BGMPath.BOMB_EXPLOSION_BGM_PATH, false, isMusicOFF);
                    bombExplosionMusicThread.start();
//                    bombExplosionMusicThread.interrupt();
                    propCrashMusicThread.start();

                    for (BombSubscriber enemy: enemyAircrafts){
                        ((BombProp)prop).addSubscriber(enemy);
                    }
                    for (BombSubscriber bullet: enemyBullets){
                        ((BombProp)prop).addSubscriber(bullet);
                    }
                    score = ((BombProp) prop).notifyAllSubscribers(score);



//                    propCrashMusicThread.interrupt();
                    //System.out.println("BombSupply active");
                }

                //TODO: 增加其他两种道具的效果
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }

    public abstract List<AbstractAircraft> createBossAircraft(List<AbstractAircraft> enemyAircrafts, int playingRound);

    public abstract int increaseEnemyMaximum(int enemyMaxNumber);

    public abstract double increaseEliteRate(double eliteEnemyRate);

    public abstract List<AbstractAircraft> createEliteAircraft(List<AbstractAircraft> enemyAircrafts, int playingRound);

    public abstract List<AbstractAircraft> createMobAircraft(List<AbstractAircraft> enemyAircrafts, int playingRound);

    public abstract int decreaseDuration(int cycleDuration);
}
