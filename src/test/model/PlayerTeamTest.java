package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.ToIntFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the PlayerTeam class
public class PlayerTeamTest {
    private PlayerTeam testPlayerTeam1;
    private PlayerTeam testPlayerTeam2;

    private Player testPlayer1;
    private Player testPlayer2;

    @BeforeEach
    public void setUp() {
        testPlayerTeam1 = new PlayerTeam("Vancouver Canucks");
        testPlayerTeam2 = new PlayerTeam("Edmonton Oilers");

        testPlayer1 = new Player("Connor McDavid", 1);
        testPlayer2 = new Player("Leon Draisaitl", 2);
    }

    @Test
    public void testPlayerTeam() {
        assertEquals("Vancouver Canucks", testPlayerTeam1.getName());
        assertEquals(0, testPlayerTeam1.getPlayers().size());
        assertEquals(0, testPlayerTeam1.getWins());
        assertEquals(0, testPlayerTeam1.getLosses());
    }

    @Test
    public void testAddPlayerOnce() {
        testPlayerTeam2.addPlayer(testPlayer1);
        assertEquals(1, testPlayerTeam2.getPlayers().size());
        assertEquals("Connor McDavid", testPlayerTeam2.getPlayers().get(0).getName());
    }

    @Test
    public void testAddPlayerMultiple() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        assertEquals(2, testPlayerTeam2.getPlayers().size());
        assertEquals("Connor McDavid", testPlayerTeam2.getPlayers().get(0).getName());
        assertEquals("Leon Draisaitl", testPlayerTeam2.getPlayers().get(1).getName());
    }

    @Test
    public void testGetTeamSpeedEmptyTeam() {
        assertEquals(0, testPlayerTeam1.getTeamSpeed());
    }

    @Test
    public void testGetTeamSpeedOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        assertEquals(10, testPlayerTeam2.getTeamSpeed());
    }

    @Test
    public void testGetTeamSpeedTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        assertEquals(30, testPlayerTeam2.getTeamSpeed());
    }

    @Test
    public void testGetTeamDefenseEmptyTeam() {
        assertEquals(0, testPlayerTeam1.getTeamDefense());
    }

    @Test
    public void testGetTeamDefenseOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        assertEquals(10, testPlayerTeam2.getTeamDefense());
    }

    @Test
    public void testGetTeamDefenseTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        assertEquals(30, testPlayerTeam2.getTeamDefense());
    }

    @Test
    public void testGetTeamOffenseEmptyTeam() {
        assertEquals(0, testPlayerTeam1.getTeamOffense());
    }

    @Test
    public void testGetTeamOffenseOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        assertEquals(10, testPlayerTeam2.getTeamOffense());
    }

    @Test
    public void testGetTeamOffenseTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        assertEquals(30, testPlayerTeam2.getTeamOffense());
    }

    @Test
    public void testGetTeamMoraleEmptyTeam() {
        assertEquals(0, testPlayerTeam1.getTeamMorale());
    }

    @Test
    public void testGetTeamMoraleOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        assertEquals(10, testPlayerTeam2.getTeamMorale());
    }

    @Test
    public void testGetTeamMoraleTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        assertEquals(30, testPlayerTeam2.getTeamMorale());
    }

    @Test
    public void testGetTeamAbstractSpeed() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        ToIntFunction<Player> fn = Player::getSpeed;
        assertEquals(30, testPlayerTeam2.getTeamAbstract(fn));
    }

    @Test
    public void testGetTeamAbstractDefense() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        ToIntFunction<Player> fn = Player::getDefense;
        assertEquals(30, testPlayerTeam2.getTeamAbstract(fn));
    }

    @Test
    public void testGetTeamAbstractOffense() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        ToIntFunction<Player> fn = Player::getOffense;
        assertEquals(30, testPlayerTeam2.getTeamAbstract(fn));
    }

    @Test
    public void testGetTeamAbstractMorale() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        ToIntFunction<Player> fn = Player::getMorale;
        assertEquals(30, testPlayerTeam2.getTeamAbstract(fn));
    }

    @Test
    public void testHandleWinEmptyTeam() {
        testPlayerTeam1.handleWin();
        assertEquals(1, testPlayerTeam1.getWins());
        assertEquals(0, testPlayerTeam1.getTeamSpeed());
        assertEquals(0, testPlayerTeam1.getTeamDefense());
        assertEquals(0, testPlayerTeam1.getTeamOffense());
        assertEquals(0, testPlayerTeam1.getTeamMorale());
    }

    @Test
    public void testHandleWinOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.handleWin();
        assertEquals(1, testPlayerTeam2.getWins());
        assertEquals(20, testPlayerTeam2.getTeamSpeed());
        assertEquals(20, testPlayerTeam2.getTeamDefense());
        assertEquals(20, testPlayerTeam2.getTeamOffense());
        assertEquals(20, testPlayerTeam2.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
    }

    @Test
    public void testHandleWinTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        testPlayerTeam2.handleWin();
        assertEquals(1, testPlayerTeam2.getWins());
        assertEquals(50, testPlayerTeam2.getTeamSpeed());
        assertEquals(50, testPlayerTeam2.getTeamDefense());
        assertEquals(50, testPlayerTeam2.getTeamOffense());
        assertEquals(50, testPlayerTeam2.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testHandleLossEmptyTeam() {
        testPlayerTeam1.handleLoss();
        assertEquals(1, testPlayerTeam1.getLosses());
        assertEquals(0, testPlayerTeam2.getTeamSpeed());
        assertEquals(0, testPlayerTeam2.getTeamDefense());
        assertEquals(0, testPlayerTeam2.getTeamOffense());
        assertEquals(0, testPlayerTeam2.getTeamMorale());
    }

    @Test
    public void testHandleLossOnePlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayer1.decrementEnergyBy(2);
        testPlayerTeam2.handleLoss();
        assertEquals(1, testPlayerTeam2.getLosses());
        assertEquals(10, testPlayerTeam2.getTeamSpeed());
        assertEquals(10, testPlayerTeam2.getTeamDefense());
        assertEquals(10, testPlayerTeam2.getTeamOffense());
        assertEquals(10, testPlayerTeam2.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
    }

    @Test
    public void testHandleLossTwoPlayerTeam() {
        testPlayerTeam2.addPlayer(testPlayer1);
        testPlayerTeam2.addPlayer(testPlayer2);
        testPlayer1.decrementEnergyBy(2);
        testPlayer2.decrementEnergyBy(2);
        testPlayerTeam2.handleLoss();
        assertEquals(1, testPlayerTeam2.getLosses());
        assertEquals(30, testPlayerTeam2.getTeamSpeed());
        assertEquals(30, testPlayerTeam2.getTeamDefense());
        assertEquals(30, testPlayerTeam2.getTeamOffense());
        assertEquals(30, testPlayerTeam2.getTeamMorale());
        assertEquals(10, testPlayer1.getEnergy());
        assertEquals(10, testPlayer2.getEnergy());
    }

    @Test
    public void testAddWin() {
        testPlayerTeam1.addWin();
        testPlayerTeam1.addWin();
        assertEquals(2, testPlayerTeam1.getWins());
    }

    @Test
    public void testAddLoss() {
        testPlayerTeam1.addLoss();
        testPlayerTeam1.addLoss();
        assertEquals(2, testPlayerTeam1.getLosses());
    }

}
