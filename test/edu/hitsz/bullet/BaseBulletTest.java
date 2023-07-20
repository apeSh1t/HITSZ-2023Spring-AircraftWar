package edu.hitsz.bullet;

import edu.hitsz.application.Game;
import edu.hitsz.application.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseBulletTest {

    private BaseBullet bullet1;
    private BaseBullet bullet2;
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        bullet1 = new BaseBullet(5, 5, 5, 5, 30);
        bullet2 = new BaseBullet(5, Main.WINDOW_HEIGHT+1, 5, 5, 30);
    }

    @DisplayName("TEST forward method")
    @Test
    void forward() {
        System.out.println("**-- Test forward method executed --**");
        double locationY = bullet1.getLocationY();
        bullet1.forward();
        assertEquals(bullet1.getLocationY(), locationY+ bullet1.getSpeedY());

        bullet2.forward();
        assertTrue(bullet2.notValid());
    }

    @DisplayName("TEST getPower method")
    @Test
    void getPower() {
        System.out.println("**-- Test getPower method executed --**");
        assertEquals(bullet1.getPower(), 30);
    }
}