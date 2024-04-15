package ui.gui;

import model.Game;
import model.Player;
import model.PlayerTeam;
import model.logging.EventLog;
import model.logging.Event;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// GUI application JFrame, manages which panel is set as the
// content pane and displayed to the user throughout use of
// the JavaGotchi application
public class MainFrame extends JFrame {
    private static final String JSON_TEAM = "./data/playerteam.json";
    private PlayerTeam team;               // user's team
    private final JsonWriter jsonWriter;   // for writing to file
    private final JsonReader jsonReader;   // for reading from file

    // EFFECTS:  constructs a new mainframe, initializing writing
    //           and reading to JSON, and setting the specifications
    //           of the application JFrame
    public MainFrame() {
        jsonWriter = new JsonWriter(JSON_TEAM);
        jsonReader = new JsonReader(JSON_TEAM);

        setTitle("JavaGotchi");
        ImageIcon icon = new ImageIcon("data/images/icon.jpg");
        setIconImage(icon.getImage());
        setLayout(new BorderLayout());
        setSize(800, 600);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEvents(EventLog.getInstance());
                dispose();
                System.exit(0);
            }
        });

        LoadMenu loadMenu = new LoadMenu(this);
        setContentPane(loadMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the exposition sequence
    //           of a new game
    public void newGame() {
        ExpositionMenu expositionMenu = new ExpositionMenu(this);
        setContentPane(expositionMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the main menu of the
    //           application
    public void mainMenu() {
        GuiMainMenu mainMenu = new GuiMainMenu(this);
        setContentPane(mainMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the pet management menu
    //           of the application
    public void petMenu() {
        GuiPetMenu petMenu = new GuiPetMenu(this, null);
        setContentPane(petMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the pet management menu
    //           of the application, retaining the given background
    //           music instead of creating a new music instance
    public void reopenPetMenu(BackgroundMusic backgroundMusic) {
        GuiPetMenu petMenu = new GuiPetMenu(this, backgroundMusic);
        setContentPane(petMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the given player's
    //           training menu, keeping the given background music
    public void trainingMenu(Player player, BackgroundMusic backgroundMusic) {
        GuiTrainingMenu trainingMenu = new GuiTrainingMenu(this, player, backgroundMusic);
        setContentPane(trainingMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the GotchiBall game
    //           sequence of the application
    public void gameMenu() {
        GuiGameMenu gameMenu = new GuiGameMenu(this);
        setContentPane(gameMenu);
    }

    // MODIFIES: this
    // EFFECTS:  sets the content pane for the results screen after
    //           a GotchiBall game, passes in a dummy BackgroundMusic
    //           to bypass looping music in favour of playing audio
    //           files a single time
    public void resultsMenu(Game game) {
        BackgroundMusic nullMusic = new BackgroundMusic("data/audio/win.wav");
        ResultsMenu resultsMenu = new ResultsMenu(this, game, nullMusic);
        setContentPane(resultsMenu);
    }

    // MODIFIES: this
    // EFFECTS:  closes the application and terminates the program
    public void close() {
        printEvents(EventLog.getInstance());
        this.dispose();
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS:  loads PlayerTeam from file, returns true if PlayerTeam
    //           is successfully loaded
    public boolean loadPlayerTeam() {
        try {
            team = jsonReader.read();
            String message = "Loaded " + team.getName() + " from " + JSON_TEAM;
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            String message = "Unable to read from file: " + JSON_TEAM;
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (JSONException e) {
            String message = "No previously saved game on file";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // EFFECTS:  saves PlayerTeam to file
    public void savePlayerTeam() {
        try {
            jsonWriter.open();
            jsonWriter.write(team);
            jsonWriter.close();
            String message = "Saved " + team.getName() + " to " + JSON_TEAM;
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            String message = "Unable to write to file: " + JSON_TEAM;
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS:  prints events in given event log to
    //           the console
    public void printEvents(EventLog eventLog) {
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS:  sets team to given PlayerTeam
    public void setTeam(PlayerTeam team) {
        this.team = team;
    }

    // MODIFIES: this
    // EFFECTS:  adds given player to team field
    public void addPlayerToTeam(Player player) {
        team.addPlayer(player);
    }

    // EFFECTS:  returns team field
    public PlayerTeam getTeam() {
        return team;
    }
}
