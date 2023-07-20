package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.aircraft.props.BulletProp;

public class BulletPropFactory implements PropsFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, double speedY, int category) {
        return new BulletProp(locationX, locationY, speedX, speedY ,category);
    }
}
