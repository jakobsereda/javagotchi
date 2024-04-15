package ui.console;

import model.PlayerTeam;
import model.Player;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// JavaGotchi game main menu
public class MainMenu {
    private static final String JSON_TEAM = "./data/playerteam.json";
    private PlayerTeam team;               // user's team
    private final Scanner input;           // scanner for handling user input
    private final JsonWriter jsonWriter;   // for writing to file
    private final JsonReader jsonReader;   // for reading from file

    // EFFECTS:  initializes writing and reading to file,
    //           if player loads a saved file, runs main
    //           menu directly, else plays intro then runs
    //           main menu
    public MainMenu() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_TEAM);
        jsonReader = new JsonReader(JSON_TEAM);

        boolean loadDecision = runLoadMenu();
        if (!loadDecision) {
            intro();
            init();
        }

        runMainMenu();
    }

    // EFFECTS:  prints the narrative game intro out to
    //           the console
    private void intro() {
        System.out.println("Welcome to JavaGotchi!");
        System.out.println("\nIn the world of JavaGotchi, all JavaGotchi pets");
        System.out.println("love playing GotchiBall! All the best GotchiBall");
        System.out.println("teams are mega-famous, and live in mega-mansions.");
        System.out.println("\nYou just quit your day job to live your dreams");
        System.out.println("as a GotchiBall team coach! You and your team of");
        System.out.println("JavaGotchi pets won't quit until the title of...");
        System.out.println("\n\tGreatest GotchiBall Team Ever!");
        System.out.println("\n...is yours.");
    }

    // MODIFIES: this
    // EFFECTS:  initializes game, prompting the user for names
    //           for their team and first pet, initializing them
    //           using given names
    private void init() {
        String teamName = initTeam();
        String petName = initPet();

        System.out.println("Officially welcoming " + petName + " to " + teamName + "!");
        System.out.println("\nTime to start training!");
    }

    // MODIFIES: this
    // EFFECTS:  prompts user for name for their team and sets
    //           their team name to given input, returns
    //           team name
    private String initTeam() {
        System.out.println("\nLet's get started. First, choose a name for your team:");

        String teamName = input.nextLine();
        team = new PlayerTeam(teamName);

        System.out.println("Great! Your GotchiBall team will be named " + teamName + ".");
        return teamName;
    }

    // MODIFIES: this
    // EFFECTS:  prompts user for name for their first pet and
    //           creates their first pet with given name and
    //           level 0, adding them to their team. returns
    //           pet name
    private String initPet() {
        System.out.println("\nNext, lets recruit your first JavaGotchi pet to join your team!");

        System.out.println("What would you like to name your first pet?");
        String petName = input.nextLine();

        Player pet = new Player(petName, 0);
        team.addPlayer(pet);
        return petName;
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  runs the main menu. displays a menu to the
    //           user and handles any input given by the user
    private void runMainMenu() {
        String command;

        while (true) {
            displayMainMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                break;
            } else {
                handleMainMenuCommand(command);
            }
        }
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  displays the main menu to the console
    private void displayMainMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("==========================");
        System.out.println("\tp  <- open pets menu");
        System.out.println("\tg  <- play GotchiBall game");
        System.out.println("\ts  <- save to file");
        System.out.println("\tq  <- quit");
    }

    // inspired by TellerApp, https://github.students.cs.ubc.ca/CPSC210/TellerApp
    // EFFECTS:  handles user input given from main menu
    private void handleMainMenuCommand(String command) {
        switch (command) {
            case "g":
                new GameMenu(team);
                break;
            case "s":
                savePlayerTeam();
                break;
            case "p":
                new PetMenu(team);
                break;
        }
    }

    // EFFECTS:  runs and handles any input for the load
    //           menu, which is launched when the application
    //           is launched, and asks the user if they would
    //           like to load a saved file. returns whether or
    //           user loaded a saved file
    private boolean runLoadMenu() {
        String command;

        while (true) {
            displayLoadMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("l")) {
                boolean loaded = loadPlayerTeam();
                if (loaded) {
                    return true;
                }
            } else if (command.equals("n")) {
                return false;
            }
        }
    }

    // EFFECTS:  displays the load menu to the console
    private void displayLoadMenu() {
        System.out.println("\nSelect from the following:");
        System.out.println("==========================");
        System.out.println("\tl  <- load game from file");
        System.out.println("\tn  <- play new game");
    }

    // MODIFIES: this
    // EFFECTS:  loads PlayerTeam from file, returns true
    //           if PlayerTeam is successfully loaded
    private boolean loadPlayerTeam() {
        try {
            team = jsonReader.read();
            System.out.println("Loaded " + team.getName() + " from " + JSON_TEAM);
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_TEAM);
            return false;
        } catch (JSONException e) {
            System.out.println("No previously saved game on file");
            return false;
        }
    }

    // EFFECTS:  saves PlayerTeam to file
    private void savePlayerTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            System.out.println("Saved " + team.getName() + " to " + JSON_TEAM);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_TEAM);
        }
    }
}