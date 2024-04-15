package ui.console;

import model.Player;
import model.PlayerTeam;

import java.util.InputMismatchException;
import java.util.Scanner;

// Menu displaying all pets on the player's team,
// allowing them to select and train individual pets
public class PetMenu {
    private final PlayerTeam team;   // team of pets displayed by menu
    private final Scanner input;     // scanner for handling user input

    // EFFECTS:  constructs a new pet menu, setting given
    //           team to the team displayed by menu,
    //           initializing scanner, and running menu
    public PetMenu(PlayerTeam team) {
        this.team = team;
        this.input = new Scanner(System.in);
        runPetMenu();
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  runs the pet menu. displays a menu to the
    //           user and handles any input given by the user
    private void runPetMenu() {
        try {
            int command;

            while (true) {
                displayPetMenu();
                command = input.nextInt();

                if (command == 0) {
                    break;
                } else {
                    handleCommandPetMenu(command);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input, please try again...");
        }
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  displays the pet menu to the screen
    private void displayPetMenu() {
        System.out.println("\nType your pets number to go");
        System.out.println("to their training menu:");
        System.out.println("===========================");
        System.out.println("\t0. back to main menu");

        int count = 1;
        for (Player player : team.getPlayers()) {
            System.out.println("\t" + count + ". " + player.getName());
            count++;
        }
    }

    // EFFECTS:  handles user input given from the pet menu,
    //           if input is invalid, throws exception
    private void handleCommandPetMenu(int command) {
        int count = 1;
        for (Player player : team.getPlayers()) {
            if (count == command) {
                new TrainingMenu(player);
                break;
            }
            count++;
        }
    }
}
