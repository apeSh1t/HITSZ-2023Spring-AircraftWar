package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.aircraft.props.BombProp;

public class BombPropFactory implements PropsFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, double speedY, int category) {
        return new BombProp(locationX, locationY, speedX, speedY, category);
    }
}
