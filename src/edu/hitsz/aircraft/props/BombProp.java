package edu.hitsz.aircraft.props;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.observer.BombSubscriber;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends AbstractProp{

    private List<BombSubscriber> bombSubscribersList = new ArrayList<>();
    public BombProp(int locationX, int locationY, int speedX, double speedY, int category) {
        super(locationX, locationY, speedX, speedY, category);
    }

    public void addSubscriber(BombSubscriber bombSubscriber){
        this.bombSubscribersList.add(bombSubscriber);
    }

    public void removeSubscriber(BombSubscriber bombSubscriber){
        this.bombSubscribersList.remove(bombSubscriber);
    }

    public int notifyAllSubscribers(int score){
        for(BombSubscriber subscriber: bombSubscribersList){
            score = subscriber.update(score);
        }
        return score;
    }
}
