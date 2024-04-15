package persistence;

import org.json.JSONObject;

// inspired by https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a piece of data that is writable
// to a .json file for persistence
public interface Writable {
    // EFFECTS:  returns this as a JSON object
    JSONObject toJson();
}
