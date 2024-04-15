package ui.gui;

import model.EnemyTeam;
import model.Game;
import model.Player;
import model.PlayerTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Application game menu that displays the events
// of a GotchiBall to the user, through simple text
// printed to the screen
public class GuiGameMenu extends TextMenu {
    private final PlayerTeam playerTeam;  // user's team
    private EnemyTeam enemyTeam;          // enemy team generated using user's team
    private Game game;                    // game to be played between both teams
    private Player starPlayer1;           // first star player to be mentioned through text
    private Player starPlayer2;           // second star player to be mentioned through text

    // EFFECTS:  constructs a new game menu with a background
    //           image and music, that displays text describing
    //           the events of a game
    public GuiGameMenu(MainFrame parent) {
        super(parent, "data/images/gamemenubackground.jpg",
                "data/audio/gamemenu.wav", 20, null);
        playerTeam = parent.getTeam();

        text = new JTextPane();
        initText(text, 20);

        JButton resultsButton = new JButton(" See Results ");
        JPanel buttonPanel = initButtonPanel(resultsButton, new Color(62, 176, 125));

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        preGame();
        playGame();

        setSize(800, 600);
        add(spacing, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        repaint();
        slowPrint("Prepping for the game...\n\nPlaying against the " + enemyTeam.getName() + "!\n\nFans are pouring"
                + " in...\n\nThe game has started!\n\n" + starPlayer1.getName() + " makes a spectacular play...\n\n"
                + "But the " + enemyTeam.getName() + " are putting up a fight...\n\n" + starPlayer2.getName()
                + " rushes forward in the last minute...\n\nThe results are in...", 100);
    }

    // MODIFIES: this
    // EFFECTS:  prepares for the game by generating a random
    //           team name for and then creating an enemy team
    public void preGame() {
        ArrayList<String> cities = csvToArrayList("data/enemyteamname/citiesprojectdata.csv");
        ArrayList<String> animals = csvToArrayList("data/enemyteamname/animalsprojectdata.csv");
        String city = cities.get((int) (Math.random() * cities.size()));
        String animal = animals.get((int) (Math.random() * animals.size()));
        enemyTeam = new EnemyTeam(city + " " + animal, playerTeam);
    }

    // MODIFIES: this
    // EFFECTS:  chooses two random players on user's team to be
    //           star players, then plays the game between user's team
    //           and enemy team
    public void playGame() {
        game = new Game(playerTeam, enemyTeam);
        starPlayer1 = playerTeam.getPlayers().get((int) (Math.random() * playerTeam.getPlayers().size()));
        starPlayer2 = playerTeam.getPlayers().get((int) (Math.random() * playerTeam.getPlayers().size()));
        game.setScoreHome();
        game.setScoreAway();
        game.playGame();
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
            String message = "Internal error reading .csv files in data package";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return arrayList;
    }

    // MODIFIES: this
    // EFFECTS:  handles user input to the results button,
    //           opening a new results menu though parent
    @Override
    public void actionPerformed(ActionEvent e) {
        backgroundMusic.endLoop();
        parent.resultsMenu(game);
    }
}
