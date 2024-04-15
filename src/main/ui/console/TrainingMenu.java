package ui.console;

import model.Player;

import java.util.Scanner;
import java.util.function.Consumer;

// Menu handling the training of an individual
// pet on the user's team in between playing games
public class TrainingMenu {
    private final Player player;   // pet being trained
    private final Scanner input;   // scanner for handling user input

    // EFFECTS:  sets player being trained to given player,
    //           initializes scanner, and runs menu
    public TrainingMenu(Player player) {
        this.player = player;
        this.input = new Scanner(System.in);
        runTrainingMenu();
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  runs the training menu. displays a menu to the
    //           user and handles any input given by the user
    private void runTrainingMenu() {
        String command;

        while (true) {
            displayTrainingMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("b")) {
                break;
            } else {
                handleCommandTrainingMenu(command);
            }
        }
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  displays the training menu to the console
    private void displayTrainingMenu() {
        System.out.println("\nTraining " + player.getName() + ":");
        System.out.println("============================");
        System.out.println("\ts  <- train speed       | current lvl: " + player.getSpeed());
        System.out.println("\to  <- train offense     | current lvl: " + player.getOffense());
        System.out.println("\td  <- train defense     | current lvl: " + player.getDefense());
        System.out.println("\tm  <- boost morale      | current lvl: " + player.getMorale());
        System.out.println("\tb  <- back to pets menu");
    }

    // EFFECTS:  handles user input given from training menu
    private void handleCommandTrainingMenu(String command) {
        switch (command) {
            case "s":
                trainSpeed();
                break;
            case "o":
                trainOffense();
                break;
            case "d":
                trainDefense();
                break;
            case "m":
                trainMorale();
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS:  trains this players speed
    private void trainSpeed() {
        trainAbstract("\nTraining speed!", Player::trainSpeed);
    }

    // MODIFIES: this
    // EFFECTS:  trains this players offense
    private void trainOffense() {
        trainAbstract("\nTraining offense!", Player::trainOffense);
    }

    // MODIFIES: this
    // EFFECTS:  trains this players defense
    private void trainDefense() {
        trainAbstract("\nTraining defense!", Player::trainDefense);
    }

    // MODIFIES: this
    // EFFECTS:  trains this players morale
    private void trainMorale() {
        trainAbstract("\nBoosting morale! We got this!", Player::boostMorale);
    }
    
    // REQUIRES: trainer is a method reference of the form:
    //              Player::X
    //           where X is boostMorale or trainY, where Y is
    //           field of Player
    // MODIFIES: this
    // EFFECTS:  trains the skill specified by trainer for the
    //           player, displaying a unique message relating
    //           to the training
    private void trainAbstract(String message, Consumer<Player> trainer) {
        if (player.getEnergy() != 0) {
            trainer.accept(player);
            System.out.println(message);
        } else {
            System.out.println("Out of energy...");
        }
    }
}
