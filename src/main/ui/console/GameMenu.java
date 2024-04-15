package ui.console;

import model.Game;
import model.Player;
import model.PlayerTeam;
import model.EnemyTeam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Menu handling GotchiBall games being played
// by the user
public class GameMenu {
    private final PlayerTeam playerTeam;   // user's team
    private final Scanner input;           // scanner for handling user input
    private EnemyTeam enemyTeam;           // randomly generated enemy team
    private Game game;                     // game to be played by this GameMenu

    // EFFECTS:  constructs a new GameMenu, with player team
    //           set to the given team, and an initialized scanner.
    //           then runs through pre, play, and post game methods
    public GameMenu(PlayerTeam team) {
        this.playerTeam = team;
        this.input = new Scanner(System.in);
        preGame();
        playGame();
        postGame();
    }

    // MODIFIES: this
    // EFFECTS:  handles pre game procedures. creates a randomly
    //           generated name for enemy team, creating an
    //           enemy team using this name
    private void preGame() {
        ArrayList<String> cities = csvToArrayList("data/enemyteamname/citiesprojectdata.csv");
        ArrayList<String> animals = csvToArrayList("data/enemyteamname/animalsprojectdata.csv");
        String city = cities.get((int) (Math.random() * cities.size()));
        String animal = animals.get((int) (Math.random() * animals.size()));
        this.enemyTeam = new EnemyTeam(city + " " + animal, playerTeam);
        System.out.println("\nPrepping for the game...");
        sleep();
    }

    // MODIFIES: this
    // EFFECTS:  sets home and away scores then plays game
    //           between player team and randomly generated
    //           enemy team, while displaying text about the
    //           game to the user, and also eventually
    //           displaying the final score
    private void playGame() {
        System.out.println("Playing against the " + enemyTeam.getName() + "!");
        sleep();
        this.game = new Game(playerTeam, enemyTeam);
        System.out.println("Fans are pouring in...");
        sleep();
        System.out.println("The game has started!");
        sleep();
        Player starPlayer1 = playerTeam.getPlayers().get((int) (Math.random() * playerTeam.getPlayers().size()));
        System.out.println(starPlayer1.getName() + " makes a spectacular play...");
        sleep();
        this.game.setScoreHome();
        System.out.println("But the " + enemyTeam.getName() + " are putting up a fight...");
        sleep();
        this.game.setScoreAway();
        Player starPlayer2 = playerTeam.getPlayers().get((int) (Math.random() * playerTeam.getPlayers().size()));
        System.out.println(starPlayer2.getName() + " rushes forward in the last minute...");
        sleep();
        this.game.playGame();
        System.out.println("The results are in...");
        sleep();
        System.out.println("\n" + playerTeam.getName() + ": " + game.getHomeScore());
        System.out.println(enemyTeam.getName() + ": " + game.getAwayScore());
    }

    // MODIFIES: this
    // EFFECTS:  handles the post game. if the users team won,
    //           then congratulates them and lets them name and
    //           add a new pet to their team with level = number
    //           of wins they have so far. if the users team lost,
    //           motivates them to try and win the next one
    private void postGame() {
        if (game.isDidHomeTeamWin()) {
            System.out.println("\nCongrats on the win!");
            System.out.println("You have a new recruit to your team,");
            System.out.println("what would you like to name them?");
            String name = input.nextLine();
            Player player = new Player(name, playerTeam.getWins());
            playerTeam.addPlayer(player);
            System.out.println("Officially welcoming " + name + " to " + playerTeam.getName());
        } else {
            System.out.println("\nYour team is sad about the loss...");
            System.out.println("But are filled with determination to do better next time!");
        }
    }

    // EFFECTS:  reads data written in csv file located at given
    //           path to an array list of strings, returns this
    //           array list
    private ArrayList<String> csvToArrayList(String path) {
        String line;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                arrayList.add(line);
            }
        } catch (IOException e) {
            System.out.println("Internal error reading .csv files in data package");
            System.out.println("Please ensure data package has not been altered");
        }
        return arrayList;
    }

    // EFFECTS:  sleeps the game for 2000 milliseconds
    private void sleep() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Internal error sleeping the system, please try again");
        }
    }
}
