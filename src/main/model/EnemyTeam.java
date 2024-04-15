package model;

import lombok.Getter;
import model.logging.Event;
import model.logging.EventLog;

import java.util.function.ToIntFunction;

// Represents an enemy GotchiBall team to the users team,
// with a name and the PlayerTeam they are the rival of
@Getter
public class EnemyTeam implements Team {
    private final String name;        // team name
    private final PlayerTeam rival;   // rival team (of this rival team)

    // EFFECTS:  constructs a RivalTeam with given name and
    //           given PlayerTeam set as their rival
    public EnemyTeam(String name, PlayerTeam rival) {
        this.name = name;
        this.rival = rival;
        EventLog.getInstance().logEvent(new Event("created new enemy team, name: " + name));
    }

    // EFFECTS:  returns the max of the result of calculating
    //           the total speed of team and 1
    @Override
    public int getTeamSpeed() {
        return Math.max(1, getTeamCalculation(PlayerTeam::getTeamSpeed));
    }

    // EFFECTS:  returns the max of the result of calculating
    //           the total defense of team and 1
    @Override
    public int getTeamDefense() {
        return Math.max(1, getTeamCalculation(PlayerTeam::getTeamDefense));
    }

    // EFFECTS:  returns the max of the result of calculating
    //           the total offense of team and 1
    @Override
    public int getTeamOffense() {
        return Math.max(1, getTeamCalculation(PlayerTeam::getTeamOffense));
    }

    // EFFECTS:  returns the max of the result of calculating
    //           the total morale of team and 1
    @Override
    public int getTeamMorale() {
        return Math.max(1, getTeamCalculation(PlayerTeam::getTeamMorale));
    }

    // REQUIRES: getter is a method reference of the form:
    //              PlayerTeam::getTeamX
    //           where X is a field of Player
    // EFFECTS:  compares expected rival team stat to the actual
    //           rival team stat specified by getter. if rival
    //           team is doing better than or the same as expected,
    //           reduces this teams score, if they are doing worse
    //           increases this teams score
    public int getTeamCalculation(ToIntFunction<PlayerTeam> getter) {
        int rivalStat = getter.applyAsInt(rival);   // team stat of rival team
        int expected = getExpectedRivalStat();      // expected team stat of rival team
        int rand = 1 + (int) (Math.random() * 4);   // random integer in [1, 4]

        if (rivalStat >= expected) {
            return rivalStat - rand;
        }
        return rivalStat + rand;
    }

    // EFFECTS:  calculates an expected team stat for rival team
    //           at current point in the game using rival team
    //           information with the following equation:
    //              (10 * wins * size) +
    //              (total games * size * (int) [1, 4])
    public int getExpectedRivalStat() {
        int size = rival.getPlayers().size();       // number of players on rival team
        int wins = rival.getWins();                 // number of rival team wins
        int loss = rival.getLosses();               // number of rival team losses
        int rand = 1 + (int) (Math.random() * 4);   // random integer in [1, 4]
        return (10 * wins * size) + ((wins + loss) * size * rand);
    }

    // EFFECTS:  handles a team win. intentionally left empty as
    //           method is not ever used but is necessary for
    //           Team interface implementation
    @Override
    public void handleWin() {
    }

    // EFFECTS:  handles a team loss. intentionally left empty as
    //           method is not ever used but is necessary for
    //           Team interface implementation
    @Override
    public void handleLoss() {
    }

    @Override
    public int getWins() {
        return 0;
    }
}

