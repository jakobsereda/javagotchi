package persistence;

import model.Player;
import model.PlayerTeam;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for the JsonWriter class
// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/persistencetesting/an\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // Exception is expected
        }
    }

    @Test
    public void testWriterEmptyPlayerTeam() {
        try {
            PlayerTeam playerTeam = new PlayerTeam("Edmonton Oilers");
            JsonWriter writer = new JsonWriter("./data/persistencetesting/testWriterEmptyPlayerTeam.json");
            writer.open();
            writer.write(playerTeam);
            writer.close();

            JsonReader reader = new JsonReader("./data/persistencetesting/testWriterEmptyPlayerTeam.json");
            playerTeam = reader.read();
            checkPlayerTeam("Edmonton Oilers", 0, 0, playerTeam);
            assertEquals(0, playerTeam.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralPlayerTeam() {
        try {
            PlayerTeam playerTeam = new PlayerTeam("The Beatles");
            Player player1 = new Player("Paul", 1);
            player1.setImagePath("yesterday.png");
            playerTeam.addPlayer(player1);
            Player player2 = new Player("John", 2);
            player2.setImagePath("julia.png");
            playerTeam.addPlayer(player2);
            playerTeam.addWin();
            playerTeam.addLoss();
            JsonWriter writer = new JsonWriter("./data/persistencetesting/testWriterGeneralPlayerTeam.json");
            writer.open();
            writer.write(playerTeam);
            writer.close();

            JsonReader reader = new JsonReader("./data/persistencetesting/testWriterGeneralPlayerTeam.json");
            playerTeam = reader.read();
            checkPlayerTeam("The Beatles", 1, 1, playerTeam);
            List<Player> players = playerTeam.getPlayers();
            assertEquals(2, players.size());
            checkPlayer("Paul", 10, 10, 10, 10, 10, "yesterday.png", players.get(0));
            checkPlayer("John", 20, 20, 20, 20, 10, "julia.png", players.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
