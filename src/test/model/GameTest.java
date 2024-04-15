package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the Game class
public class GameTest {
    private Game testGame;

    private PlayerTeam testPlayerTeam;
    private EnemyTeam testEnemyTeam;

    private Player testPlayer1;
    private Player testPlayer2;

    @BeforeEach
    public void setUp() {
        testPlayerTeam = new PlayerTeam("Edmonton Oilers");
        testPlayerTeam.addWin();
        testPlayerTeam.addLoss();

        testPlayer1 = new Player("Connor McDavid", 1);
        testPlayer2 = new Player("Leon Draisaitl", 2);
        testPlayerTeam.addPlayer(testPlayer1);
        testPlayerTeam.addPlayer(testPlayer2);

        testEnemyTeam = new EnemyTeam("Calgary Flames", testPlayerTeam);

        testGame = new Game(testPlayerTeam, testEnemyTeam);
    }

    @Test
    public void testGame() {
        assertEquals(testPlayerTeam, testGame.getHome());
        assertEquals(testEnemyTeam, testGame.getAway());
    }

    @Test
    public void testSetHomeScore() {
        int upMorale = testPlayerTeam.getTeamMorale() / (testPlayerTeam.getTeamMorale() - 4);
        int upSpeed = testPlayerTeam.getTeamSpeed() / (testPlayerTeam.getTeamSpeed() - 4);
        int upper = ((testPlayerTeam.getTeamOffense() - (testPlayerTeam.getTeamDefense() - 4)) * upMorale) + upSpeed;

        testGame.setScoreHome();
        assertTrue(1 <= testGame.getHomeScore());
        assertTrue(upper >= testGame.getHomeScore());
    }

    @Test
    public void testSetAwayScore() {
        int upMorale = (testPlayerTeam.getTeamMorale() + 4) / testPlayerTeam.getTeamMorale();
        int upSpeed = (testPlayerTeam.getTeamSpeed() + 4) / testPlayerTeam.getTeamSpeed();
        int upper = (((testPlayerTeam.getTeamOffense() + 4) - testPlayerTeam.getTeamDefense()) * upMorale) + upSpeed;

        testGame.setScoreAway();
        assertTrue(1 <= testGame.getAwayScore());
        assertTrue(upper >= testGame.getAwayScore());
    }

    @Test
    public void testSetScoreAbstractHomeAttacking() {
        int upMorale = testPlayerTeam.getTeamMorale() / (testPlayerTeam.getTeamMorale() - 4);
        int upSpeed = testPlayerTeam.getTeamSpeed() / (testPlayerTeam.getTeamSpeed() - 4);
        int upper = ((testPlayerTeam.getTeamOffense() - (testPlayerTeam.getTeamDefense() - 4)) * upMorale) + upSpeed;
        assertTrue(1 <= testGame.setScoreAbstract(testPlayerTeam, testEnemyTeam));
        assertTrue(upper >= testGame.setScoreAbstract(testPlayerTeam, testEnemyTeam));
    }

    @Test
    public void testSetScoreAbstractAwayAttacking() {
        int upMorale = (testPlayerTeam.getTeamMorale() + 4) / testPlayerTeam.getTeamMorale();
        int upSpeed = (testPlayerTeam.getTeamSpeed() + 4) / testPlayerTeam.getTeamSpeed();
        int upper = (((testPlayerTeam.getTeamOffense() + 4) - testPlayerTeam.getTeamDefense()) * upMorale) + upSpeed;
        assertTrue(1 <= testGame.setScoreAbstract(testEnemyTeam, testPlayerTeam));
        assertTrue(upper >= testGame.setScoreAbstract(testEnemyTeam, testPlayerTeam));
    }

    @Test
    public void testPlayGameEqualScores() {
        testGame.setAwayScoreForTesting(2);
        testGame.setHomeScoreForTesting(2);
        testGame.playGame();

        assertTrue(testGame.isDidHomeTeamWin());
        assertEquals(2, testGame.getAwayScore());
        assertEquals(3, testGame.getHomeScore());

        assertEquals(2, testPlayerTeam.getWins());
        assertEquals(50, testPlayerTeam.getTeamSpeed());
        assertEquals(50, testPlayerTeam.getTeamDefense());
        assertEquals(50, testPlayerTeam.getTeamOffense());
        assertEquals(50, testPlayerTeam.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testPlayGameHomeScoresHigher() {
        testGame.setAwayScoreForTesting(2);
        testGame.setHomeScoreForTesting(3);
        testGame.playGame();

        assertTrue(testGame.isDidHomeTeamWin());
        assertEquals(2, testGame.getAwayScore());
        assertEquals(3, testGame.getHomeScore());

        assertEquals(2, testPlayerTeam.getWins());
        assertEquals(50, testPlayerTeam.getTeamSpeed());
        assertEquals(50, testPlayerTeam.getTeamDefense());
        assertEquals(50, testPlayerTeam.getTeamOffense());
        assertEquals(50, testPlayerTeam.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testPlayGameHomeScoresLower() {
        testGame.setAwayScoreForTesting(3);
        testGame.setHomeScoreForTesting(2);
        testGame.playGame();

        assertFalse(testGame.isDidHomeTeamWin());
        assertEquals(3, testGame.getAwayScore());
        assertEquals(2, testGame.getHomeScore());

        assertEquals(2, testPlayerTeam.getLosses());
        assertEquals(30, testPlayerTeam.getTeamSpeed());
        assertEquals(30, testPlayerTeam.getTeamDefense());
        assertEquals(30, testPlayerTeam.getTeamOffense());
        assertEquals(30, testPlayerTeam.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
        assertEquals(10, testPlayer2.getEnergy());
    }

}
