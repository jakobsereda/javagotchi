package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for the Player class
class PlayerTest {
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    @BeforeEach
    public void setUp() {
        testPlayer1 = new Player("Paul", 0);
        testPlayer2 = new Player("John", 1);
        testPlayer3 = new Player("Joe Biden", 1);
    }

    @Test
    public void testPlayerZeroLevel() {
        assertEquals("Paul", testPlayer1.getName());
        assertEquals(0, testPlayer1.getSpeed());
        assertEquals(0, testPlayer1.getDefense());
        assertEquals(0, testPlayer1.getOffense());
        assertEquals(0, testPlayer1.getMorale());
        assertEquals(10, testPlayer1.getEnergy());
    }

    @Test
    public void testPlayerNonZeroLevel() {
        assertEquals("John", testPlayer2.getName());
        assertEquals(10, testPlayer2.getSpeed());
        assertEquals(10, testPlayer2.getDefense());
        assertEquals(10, testPlayer2.getOffense());
        assertEquals(10, testPlayer2.getMorale());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainSpeedNoEnergy() {
        testPlayer1.decrementEnergyBy(10);
        testPlayer1.trainSpeed();
        assertEquals(0, testPlayer1.getSpeed());
        assertEquals(0, testPlayer1.getEnergy());
    }

    @Test
    public void testTrainSpeedEqualAndLowMorale() {
        testPlayer2.trainSpeed();
        assertEquals(11, testPlayer2.getSpeed());
        assertEquals(9, testPlayer2.getEnergy());
        testPlayer2.trainSpeed();
        assertEquals(12, testPlayer2.getSpeed());
        assertEquals(8, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainSpeedHighMorale() {
        testPlayer2.boostMorale();
        testPlayer2.trainSpeed();
        assertEquals(12, testPlayer2.getSpeed());
        assertEquals(8, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainDefenseNoEnergy() {
        testPlayer1.decrementEnergyBy(10);
        testPlayer1.trainDefense();
        assertEquals(0, testPlayer1.getDefense());
        assertEquals(0, testPlayer1.getEnergy());
    }

    @Test
    public void testTrainDefenseEqualAndLowMorale() {
        testPlayer2.trainDefense();
        assertEquals(11, testPlayer2.getDefense());
        assertEquals(9, testPlayer2.getEnergy());
        testPlayer2.trainDefense();
        assertEquals(12, testPlayer2.getDefense());
        assertEquals(8, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainDefenseHighMorale() {
        testPlayer2.boostMorale();
        testPlayer2.trainDefense();
        assertEquals(12, testPlayer2.getDefense());
        assertEquals(8, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainOffenseNoEnergy() {
        testPlayer1.decrementEnergyBy(10);
        testPlayer1.trainOffense();
        assertEquals(0, testPlayer1.getOffense());
        assertEquals(0, testPlayer1.getEnergy());
    }

    @Test
    public void testTrainOffenseEqualAndLowMorale() {
        testPlayer2.trainOffense();
        assertEquals(11, testPlayer2.getOffense());
        assertEquals(9, testPlayer2.getEnergy());
        testPlayer2.trainOffense();
        assertEquals(12, testPlayer2.getOffense());
        assertEquals(8, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainOffenseHighMorale() {
        testPlayer2.boostMorale();
        testPlayer2.trainOffense();
        assertEquals(12, testPlayer2.getOffense());
        assertEquals(8, testPlayer2.getEnergy());
    }
    
    @Test
    public void testTrainAbstractSpeed() {
        int speed = testPlayer2.getSpeed();
        IntConsumer fn = (int s) -> testPlayer2.setSpeed(s);
        testPlayer2.trainAbstract(speed, "speed", fn);
        assertEquals(11, testPlayer2.getSpeed());
        assertEquals(9, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainAbstractDefense() {
        int defense = testPlayer2.getDefense();
        IntConsumer fn = (int d) -> testPlayer2.setDefense(d);
        testPlayer2.trainAbstract(defense, "defense", fn);
        assertEquals(11, testPlayer2.getDefense());
        assertEquals(9, testPlayer2.getEnergy());
    }

    @Test
    public void testTrainAbstractOffense() {
        int offense = testPlayer2.getOffense();
        IntConsumer fn = (int o) -> testPlayer2.setOffense(o);
        testPlayer2.trainAbstract(offense, "offense", fn);
        assertEquals(11, testPlayer2.getOffense());
        assertEquals(9, testPlayer2.getEnergy());
    }

    @Test
    public void testBoostMoraleZeroMorale() {
        testPlayer1.boostMorale();
        assertEquals(1, testPlayer1.getMorale());
        assertEquals(9, testPlayer1.getEnergy());
    }

    @Test
    public void testBoostMoraleNonZeroMorale() {
        testPlayer2.boostMorale();
        assertEquals(11, testPlayer2.getMorale());
        assertEquals(9, testPlayer2.getEnergy());
    }

    @Test
    public void testBoostMoraleNoEnergy() {
        assertEquals(10, testPlayer2.getMorale());
        testPlayer2.setEnergy(0);
        testPlayer2.boostMorale();
        assertEquals(10, testPlayer2.getMorale());
    }

    @Test
    public void testLevelUpFullEnergy() {
        testPlayer2.levelUp();
        assertEquals(20, testPlayer2.getSpeed());
        assertEquals(20, testPlayer2.getDefense());
        assertEquals(20, testPlayer2.getOffense());
        assertEquals(20, testPlayer2.getMorale());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testLevelUpNonFullEnergy() {
        testPlayer2.decrementEnergyBy(10);
        testPlayer2.levelUp();
        assertEquals(20, testPlayer2.getSpeed());
        assertEquals(20, testPlayer2.getDefense());
        assertEquals(20, testPlayer2.getOffense());
        assertEquals(20, testPlayer2.getMorale());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testResetEnergyFullEnergy() {
        testPlayer2.resetEnergy();
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testResetEnergyNonFullEnergy() {
        testPlayer2.decrementEnergyBy(10);
        testPlayer2.resetEnergy();
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testDecrementEnergyByZero() {
        testPlayer2.decrementEnergyBy(0);
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testDecrementEnergyByMax() {
        testPlayer2.decrementEnergyBy(10);
        assertEquals(0, testPlayer2.getEnergy());
    }

    @Test
    public void testGenerateImagePathJoe() {
        testPlayer3.generateImagePath();
        assertEquals("data/images/petsprites/joe", testPlayer3.getImagePath());
    }

    @Test
    public void testGenerateImagePathGeneral() {
        testPlayer2.generateImagePath();
        List<String> possiblePaths = new ArrayList<>();
        for (int i = 1; i < 6; i++){
            possiblePaths.add("data/images/petsprites/" + i);
        }
        assertTrue(possiblePaths.contains(testPlayer2.getImagePath()));
    }
}