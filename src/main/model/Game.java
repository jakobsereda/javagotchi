package model;

import model.logging.Event;
import model.logging.EventLog;

// Represents a GotchiBall game between a home and
// away team, (who are rivals), scores for both teams,
// and an indicator if the home team won or not
public class Game {
    private final Team home;          // home team (always users team)
    private final Team away;          // away team (always an enemy team of user's team)
    private int homeScore;            // points scored by home team
    private int awayScore;            // points scored by away team
    private boolean didHomeTeamWin;   // indicates if home team won or lost

    // REQUIRES: away.getRival() == home
    // EFFECTS:  constructs a game with a home team and
    //           an away team
    public Game(PlayerTeam home, EnemyTeam away) {
        this.home = home;
        this.away = away;
        EventLog.getInstance().logEvent(new Event("created new game, "
                + "home: " + home.getName() + "; away: " + away.getName()));
    }

    // MODIFIES: this
    // EFFECTS:  calculates the amount of points scored
    //           by the home team
    public void setScoreHome() {
        homeScore = setScoreAbstract(home, away);
    }

    // MODIFIES: this
    // EFFECTS:  calculates the amount of points scored
    //           by the away team
    public void setScoreAway() {
        awayScore = setScoreAbstract(away, home);
    }

    // EFFECTS:  calculates the amount of points scored
    //           by attacker using following formula:
    //              [(attacker offense - defender defense)
    //              * (attacker morale / defender morale)]
    //              + (attacker speed / defender speed)
    //           returning Max(1, calculated score)
    public int setScoreAbstract(Team attacker, Team defender) {
        int baseScore = attacker.getTeamOffense() - defender.getTeamDefense();
        int moraleMultiplier = attacker.getTeamMorale() / Math.max(defender.getTeamMorale(), 1);
        int speedBoost = attacker.getTeamSpeed() / Math.max(defender.getTeamSpeed(), 1);
        return Math.max(1, (baseScore * moraleMultiplier) + speedBoost);
    }

    // REQUIRES: have already called setHomeScore() && setAwayScore()
    // MODIFIES: this
    // EFFECTS:  determines the outcome of the game based on scores
    //           if home and away both scored the same amount,
    //           the home score will be incremented and the home team
    //           will win. otherwise, the higher scoring team wins
    public void playGame() {
        if (homeScore == awayScore) {
            homeScore++;
            didHomeTeamWin = true;
            EventLog.getInstance().logEvent(new Event(home.getName() + " won the game"));
            home.handleWin();
        } else if (homeScore > awayScore) {
            didHomeTeamWin = true;
            EventLog.getInstance().logEvent(new Event(home.getName() + " won the game"));
            home.handleWin();
        } else {
            didHomeTeamWin = false;
            EventLog.getInstance().logEvent(new Event(away.getName() + " won the game"));
            home.handleLoss();
        }
    }

    // MODIFIES: this
    // EFFECTS:  sets homeScore to given score
    //           (made for testing)
    public void setHomeScoreForTesting(int homeScore) {
        this.homeScore = homeScore;
    }

    // MODIFIES: this
    // EFFECTS:  sets awayScore to given score
    //           (made for testing)
    public void setAwayScoreForTesting(int awayScore) {
        this.awayScore = awayScore;
    }

    public Team getHome() {
        return home;
    }

    public Team getAway() {
        return away;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public boolean getDidHomeTeamWin() {
        return didHomeTeamWin;
    }
}
