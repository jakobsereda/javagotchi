package persistence;

import model.Player;
import model.PlayerTeam;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a JSON reader that reads information
// about PlayerTeam and Players from a memory file
public class JsonReader {
    private final String source;

    // EFFECTS:  constructs a new reader to read from
    //           source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS:  reads PlayerTeam from file and returns
    //           it, throws IOException if an error occurs
    public PlayerTeam read() throws IOException {
        String jsonData = readFile(source);
        JSONObject json = new JSONObject(jsonData);
        return parsePlayerTeam(json);
    }

    // EFFECTS:  reads and returns source file as string
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS:  parses PlayerTeam from JSON object and
    //           returns it
    private PlayerTeam parsePlayerTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int wins = jsonObject.getInt("wins");
        int losses = jsonObject.getInt("losses");
        PlayerTeam playerTeam = new PlayerTeam(name);
        playerTeam.setWins(wins);
        playerTeam.setLosses(losses);
        addPlayers(playerTeam, jsonObject);
        return playerTeam;
    }

    // MODIFIES: playerTeam
    // EFFECTS:  parses Players from JSON object and
    //           adds them to PlayerTeam
    private void addPlayers(PlayerTeam playerTeam, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(playerTeam, nextPlayer);
        }
    }

    // MODIFIES: playerTeam
    // EFFECTS:  parses Player from JSON object and adds
    //           it to PlayerTeam
    private void addPlayer(PlayerTeam playerTeam, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int speed = jsonObject.getInt("speed");
        int offense = jsonObject.getInt("offense");
        int defense = jsonObject.getInt("defense");
        int morale = jsonObject.getInt("morale");
        int energy = jsonObject.getInt("energy");
        String imagePath = jsonObject.getString("imagePath");
        Player player = new Player(name, 0);
        player.setSpeed(speed);
        player.setOffense(offense);
        player.setDefense(defense);
        player.setMorale(morale);
        player.setEnergy(energy);
        player.setImagePath(imagePath);
        playerTeam.addPlayer(player);
    }
}
