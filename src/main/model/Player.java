package model;

import model.logging.Event;
import model.logging.EventLog;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Random;
import java.util.function.IntConsumer;

// Represents a pet adopted by the user, who is a player on their
// GotchiBall team, with a name and stats (speed, defense, offense,
// morale) represented as integers. Players also have an energy
// level, which is an integer in [1, 10].
public class Player implements Writable {
    private final String name;   // player name
    private int speed;           // speed stat
    private int defense;         // defense stat
    private int offense;         // offense stat
    private int morale;          // player morale
    private int energy;          // player energy
    private String imagePath;    // path to players sprite folder

    // EFFECTS:  constructs a new player with given name, all
    //           stats set to their level * 10, and full energy
    public Player(String name, int level) {
        int stat = level * 10;
        this.name = name;
        speed = stat;
        defense = stat;
        offense = stat;
        morale = stat;
        energy = 10;
        EventLog.getInstance().logEvent(new Event("created new player, name: " + name + ", level: " + level));
    }

    // MODIFIES: this
    // EFFECTS:  if player has not run out of energy, increment
    //           the player's speed and decrement their energy,
    //           speed is incremented twice if morale > speed
    public void trainSpeed() {
        trainAbstract(speed, "speed", (int speed) -> this.speed = speed);
    }

    // MODIFIES: this
    // EFFECTS:  if player has not run out of energy, increment
    //           the player's defense and decrement their energy,
    //           defense is incremented twice if morale > defense
    public void trainDefense() {
        trainAbstract(defense, "defense", (int defense) -> this.defense = defense);
    }

    // MODIFIES: this
    // EFFECTS:  if player has not run out of energy, increment
    //           the player's offense and decrement their energy,
    //           offense is incremented twice if morale > offense
    public void trainOffense() {
        trainAbstract(offense, "offense", (int offense) -> this.offense = offense);
    }

    // REQUIRES: setter is a lambda expression of the form:
    //              (int x) -> this.x = x
    //           where x = field
    // MODIFIES: this
    // EFFECTS:  if player has not run out of energy, increment
    //           the given field and decrement their energy,
    //           given field is incremented twice if morale > field
    public void trainAbstract(int field, String title, IntConsumer setter) {
        if ((energy > 0) && (morale > field)) {
            field += 2;
            energy--;
            EventLog.getInstance().logEvent(new Event("training " + name + "'s " + title));
        } else if (energy > 0) {
            field++;
            energy--;
            EventLog.getInstance().logEvent(new Event("training " + name + "'s " + title));
        }
        setter.accept(field);
    }

    // MODIFIES: this
    // EFFECTS:  increments the player's morale stat and
    //           decrements their energy stat
    public void boostMorale() {
        if (energy > 0) {
            morale++;
            energy--;
            EventLog.getInstance().logEvent(new Event("boosting " + name + "'s morale"));
        }
    }

    // MODIFIES: this
    // EFFECTS:  increases all player stats by 10, resets
    //           energy to full
    public void levelUp() {
        speed += 10;
        defense += 10;
        offense += 10;
        morale += 10;
        resetEnergy();
        EventLog.getInstance().logEvent(new Event(name + " levelled up"));
    }

    // MODIFIES: this
    // EFFECTS:  resets player energy to full
    public void resetEnergy() {
        energy = 10;
    }

    // REQUIRES: amount <= getEnergy()
    // MODIFIES: this
    // EFFECTS:  decrements players energy by given amount
    //           (made for testing)
    public void decrementEnergyBy(int amount) {
        energy -= amount;
    }

    // MODIFIES: this
    // EFFECTS:  sets player imagePath to a random sprite
    //           folder in images/petsprites, unless player is
    //           named 'joe biden', in which case there is a
    //           special secret folder for their sprites
    public void generateImagePath() {
        if (name.equalsIgnoreCase("joe biden")) {
            imagePath = "data/images/petsprites/joe";
        } else {
            Random rand = new Random();
            int randomNum = 1 + rand.nextInt(5);
            imagePath = "data/images/petsprites/" + randomNum;
        }
    }

    // EFFECTS:  returns Player as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("speed", speed);
        json.put("defense", defense);
        json.put("offense", offense);
        json.put("morale", morale);
        json.put("energy", energy);
        json.put("imagePath", imagePath);
        return json;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setOffense(int offense) {
        this.offense = offense;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDefense() {
        return defense;
    }

    public int getOffense() {
        return offense;
    }

    public int getMorale() {
        return morale;
    }

    public int getEnergy() {
        return energy;
    }

    public String getImagePath() {
        return imagePath;
    }
}
