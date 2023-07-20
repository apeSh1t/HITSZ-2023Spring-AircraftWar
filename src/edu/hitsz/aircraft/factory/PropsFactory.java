package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.props.AbstractProp;

public interface PropsFactory {
    public AbstractProp createProp(int locationX, int locationY, int speedX, double speedY, int category);
}
