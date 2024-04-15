package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.ToIntFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Unit tests for the EnemyTeam class
public class EnemyTeamTest {
    private EnemyTeam testEnemyTeam;
    private PlayerTeam testPlayerTeam;

    @BeforeEach
    public void setUp() {
        testPlayerTeam = new PlayerTeam("Edmonton Oilers");
        testPlayerTeam.addWin();
        testPlayerTeam.addWin();
        testPlayerTeam.addLoss();

        Player testPlayer1 = new Player("Connor McDavid", 1);
        Player testPlayer2 = new Player("Leon Draisaitl", 2);
        testPlayerTeam.addPlayer(testPlayer1);
        testPlayerTeam.addPlayer(testPlayer2);

        testEnemyTeam = new EnemyTeam("Calgary Flames", testPlayerTeam);
    }

    @Test
    public void testEnemyTeam() {
        assertEquals("Calgary Flames", testEnemyTeam.getName());
        assertEquals(testPlayerTeam, testEnemyTeam.getRival());
    }

    @Test
    public void testGetTeamSpeed() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamSpeed;
        int rivalStat = testPlayerTeam.getTeamSpeed();
        int lower = 1;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamDefense() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamDefense;
        int rivalStat = testPlayerTeam.getTeamDefense();
        int lower = 1;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamOffense() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamOffense;
        int rivalStat = testPlayerTeam.getTeamOffense();
        int lower = 1;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamMorale() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamMorale;
        int rivalStat = testPlayerTeam.getTeamMorale();
        int lower = 1;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamCalculationSpeed() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamSpeed;
        int rivalStat = testPlayerTeam.getTeamSpeed();
        int lower = rivalStat - 4;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamCalculationDefense() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamDefense;
        int rivalStat = testPlayerTeam.getTeamDefense();
        int lower = rivalStat - 4;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamCalculationOffense() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamOffense;
        int rivalStat = testPlayerTeam.getTeamOffense();
        int lower = rivalStat - 4;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetTeamCalculationMorale() {
        ToIntFunction<PlayerTeam> fn = PlayerTeam::getTeamMorale;
        int rivalStat = testPlayerTeam.getTeamMorale();
        int lower = rivalStat - 4;
        int upper = rivalStat + 4;
        assertTrue(lower <= testEnemyTeam.getTeamCalculation(fn));
        assertTrue(upper >= testEnemyTeam.getTeamCalculation(fn));
    }

    @Test
    public void testGetExpectedRivalStat() {
        int lower = (10 * 2 * 2) + ((2 + 1) * 2);
        int upper = (10 * 2 * 2) + ((2 + 1) * 2 * 4);
        assertTrue(lower <= testEnemyTeam.getExpectedRivalStat());
        assertTrue(upper >= testEnemyTeam.getExpectedRivalStat());
    }

    @Test
    public void testHandleWin() {
        testEnemyTeam.handleWin();
        assertEquals("Calgary Flames", testEnemyTeam.getName());
        assertEquals(testPlayerTeam, testEnemyTeam.getRival());
    }

    @Test
    public void testHandleLoss() {
        testEnemyTeam.handleLoss();
        assertEquals("Calgary Flames", testEnemyTeam.getName());
        assertEquals(testPlayerTeam, testEnemyTeam.getRival());
    }
}
