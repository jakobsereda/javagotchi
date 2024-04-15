package persistence;

import model.Player;
import model.PlayerTeam;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the JsonReader class
// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/persistencetesting/madeUpFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // Exception is expected
        }
    }

    @Test
    public void testReaderEmptyPlayerTeam() {
        JsonReader reader = new JsonReader("./data/persistencetesting/testReaderEmptyPlayerTeam.json");
        try {
            PlayerTeam playerTeam = reader.read();
            checkPlayerTeam("My team", 0, 0, playerTeam);
            assertEquals(0, playerTeam.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneralPlayerTeam() {
        JsonReader reader = new JsonReader("./data/persistencetesting/testReaderGeneralPlayerTeam.json");
        try {
            PlayerTeam playerTeam = reader.read();
            checkPlayerTeam("My team 2", 1, 1, playerTeam);
            List<Player> players = playerTeam.getPlayers();
            checkPlayer("Paul", 12, 10, 14, 10, 4, "yesterday.png", players.get(0));
            checkPlayer("John", 18, 11, 10, 12, 2, "julia.png", players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
