package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft heroAircraft1;
    private HeroAircraft heroAircraft2;
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        heroAircraft1 = HeroAircraft.getSingleton(5, 5, 5, 5, 80);
        heroAircraft2 = HeroAircraft.getSingleton(1, 1, 1, 1, 40);
    }

    @DisplayName("TEST getSingleton method")
    @Test
    void shoot() {
        System.out.println("**-- Test getSingleton method executed --**");
        assertEquals(heroAircraft1.getLocationX(), heroAircraft2.getLocationX());

    }
    @DisplayName("TEST increaseHp method")
    @Test
    void increaseHp() {
        System.out.println("**-- Test increaseHp method executed --**");
        heroAircraft1.increaseHp(30);
        assertEquals(heroAircraft1.getHp(), heroAircraft1.maxHp);
        heroAircraft1.increaseHp(-110);
        assertEquals(heroAircraft1.getHp(), 0);
        assertTrue(heroAircraft1.notValid());
    }


}
