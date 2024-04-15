package persistence;

import model.Player;
import model.PlayerTeam;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for the classes in persistence
// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkPlayerTeam(String name, int wins, int losses, PlayerTeam playerTeam) {
        assertEquals(name, playerTeam.getName());
        assertEquals(wins, playerTeam.getWins());
        assertEquals(losses, playerTeam.getLosses());
    }

    protected void checkPlayer(String name, int spd, int def, int off, int mor, int egy, String imgPth, Player p) {
        assertEquals(name, p.getName());
        assertEquals(spd, p.getSpeed());
        assertEquals(def, p.getDefense());
        assertEquals(off, p.getOffense());
        assertEquals(mor, p.getMorale());
        assertEquals(egy, p.getEnergy());
        assertEquals(imgPth, p.getImagePath());
    }
}
