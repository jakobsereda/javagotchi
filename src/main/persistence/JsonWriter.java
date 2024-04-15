package persistence;

import model.PlayerTeam;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a JSON writer that writes information
// about PlayerTeam and Players to a memory file
public class JsonWriter {
    private static final int TAB = 4;
    private final String destination;
    private PrintWriter writer;

    // EFFECTS:  constructs a new JsonWriter to write
    //           information to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS:  opens writer, throws FileNotFoundException
    //           if destination file fails to open
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS:  writes JSON representation of given
    //           PlayerTeam to file
    public void write(PlayerTeam playerTeam) {
        JSONObject json = playerTeam.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS:  closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS:  writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
