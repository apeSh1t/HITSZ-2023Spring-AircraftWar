package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.aircraft.props.BloodProp;

public class BloodPropFactory implements PropsFactory{
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, double speedY, int category) {
        return new BloodProp(locationX, locationY, speedX, speedY, category);
    }
}
