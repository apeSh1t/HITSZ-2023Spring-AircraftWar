package edu.hitsz.aircraft;

import edu.hitsz.aircraft.props.AbstractProp;
import edu.hitsz.aircraft.props.BloodProp;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EliteEnemyTest {

    private AbstractAircraft eliteEnemy;

    @BeforeEach
    void setUp(){
        System.out.println("**--- Executed before each test method in this class ---**");
        eliteEnemy = new EliteEnemy(5, 5, 0, 5, 30);
    }

    @DisplayName("TEST shoot method")
    @Test
    void shoot() {
        System.out.println("**-- Test shoot method executed --**");
        List<BaseBullet> bullets = eliteEnemy.shoot();
        assertNotNull(bullets);
        for (BaseBullet bullet:bullets){
            assertEquals(bullet.getPower(), 30);
            assertEquals(bullet.getSpeedY(), eliteEnemy.getSpeedY() + 5);
            assertEquals(bullet.getLocationX(), eliteEnemy.getLocationX());
        }

    }

    @DisplayName("TEST drop method")
    @Test
    void drop() {
        System.out.println("**-- Test drop method executed --**");
        List<AbstractProp> props = eliteEnemy.drop();
        for (AbstractProp prop:props){
            assertEquals(prop.getLocationY(), eliteEnemy.getLocationY()+2);
            assertEquals(prop.getSpeedY(), (eliteEnemy.getSpeedY()+5)/3);
            assertTrue(1 <= prop.getCategory() && prop.getCategory()<= 3);
            if (prop instanceof BloodProp){
                assertEquals(prop.getIncrease(), 30);
            }
        }
    }
}