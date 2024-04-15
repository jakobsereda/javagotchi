package model;

// Represents a team that will compete in a game of
// GotchiBall. Teams that compete in GotchiBall must
// have methods to handle a win/loss and that allow
// for easy access to the four stats used to calculate
// score: speed, offense, defense, and morale
public interface Team {
    // EFFECTS:  returns the result of calculating the total
    //           speed of all players on a given team
    int getTeamSpeed();

    // EFFECTS:  returns the result of calculating the total
    //           defense of all players on a given team
    int getTeamDefense();

    // EFFECTS:  returns the result of calculating the total
    //           offense of all players on a given team
    int getTeamOffense();

    // EFFECTS:  returns the result of calculating the total
    //           morale of all players on a given team
    int getTeamMorale();

    // EFFECTS:  handles a team win
    void handleWin();

    // EFFECTS:  handles a team loss
    void handleLoss();

    // EFFECTS:  returns team name
    String getName();

    // EFFECTS:  returns number of team wins
    int getWins();
}