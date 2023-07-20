package edu.hitsz.observer;

public interface BombSubscriber {
    /**
     * 对炸弹道具的反应
     *
     * @return
     */
    int update(int score);
}
