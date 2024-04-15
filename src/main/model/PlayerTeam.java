package model;

import lombok.Getter;
import lombok.Setter;
import model.logging.Event;
import model.logging.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

// Represents a GotchiBall team (owned by the user) with a name,
// a list of all players on the team, and counters of how many
// games the team has won and lost
@Getter
@Setter
public class PlayerTeam implements Team, Writable {
    private final String name;            // team name
    private final List<Player> players;   // list of players on team
    private int wins;                     // number of games won by team
    private int losses;                   // number of games lost by team

    // EFFECTS:  constructs a new PlayerTeam with given name,
    //           no players, and no wins or losses
    public PlayerTeam(String name) {
        this.name = name;
        players = new ArrayList<>();
        wins = 0;
        losses = 0;
        EventLog.getInstance().logEvent(new Event("created new player team, name: " + name));
    }

    // MODIFIES: this
    // EFFECTS:  adds a new player to the list of players
    public void addPlayer(Player player) {
        players.add(player);
        EventLog.getInstance().logEvent(new Event("added " + player.getName() + " to " + name));
    }

    // EFFECTS:  returns the sum of the speed stats of all
    //           individual players on the team
    @Override
    public int getTeamSpeed() {
        return getTeamAbstract(Player::getSpeed);
    }

    // EFFECTS:  returns the sum of the defense stats of all
    //           individual players on the team
    @Override
    public int getTeamDefense() {
        return getTeamAbstract(Player::getDefense);
    }

    // EFFECTS:  returns the sum of the offense stats of all
    //           individual players on the team
    @Override
    public int getTeamOffense() {
        return getTeamAbstract(Player::getOffense);
    }

    // EFFECTS:  returns the sum of the morale stats of all
    //           individual players on the team
    @Override
    public int getTeamMorale() {
        return getTeamAbstract(Player::getMorale);
    }

    // REQUIRES: getter is a method reference of the form:
    //              Player::getX
    //           where X is a field of Player
    // EFFECTS:  returns the sum of all players stats for a
    //           given field, specified by the method reference
    //           passed to the method
    public int getTeamAbstract(ToIntFunction<Player> getter) {
        int count = 0;
        for (Player player : players) {
            count += getter.applyAsInt(player);
        }
        return count;
    }

    // MODIFIES: this
    // EFFECTS:  adds a win to the count of wins, then
    //           levels up all players on the team
    public void handleWin() {
        addWin();
        for (Player player : players) {
            player.levelUp();
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds a loss to the count of losses, then
    //           resets the energy of all players on the team
    public void handleLoss() {
        addLoss();
        for (Player player : players) {
            player.resetEnergy();
        }
    }

    // MODIFIES: this
    // EFFECTS:  adds a win to the count of wins
    public void addWin() {
        wins++;
        EventLog.getInstance().logEvent(new Event("added win to count of wins for " + name));
    }

    // MODIFIES: this
    // EFFECTS:  adds a loss to the count of losses
    public void addLoss() {
        losses++;
        EventLog.getInstance().logEvent(new Event("added loss to count of losses for " + name));
    }

    // EFFECTS:  returns PlayerTeam as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("wins", wins);
        json.put("losses", losses);
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS:  returns Players on this team as a
    //           JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player player : players) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }
}
