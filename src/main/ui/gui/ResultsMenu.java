package ui.gui;

import model.Game;
import model.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

// Application results menu that types the results
// of a game to the screen
public class ResultsMenu extends TextMenu {
    private final Game game;   // game to show the results of

    // EFFECTS:  constructs a new results menu with a
    //           background image and no music; initialize
    //           printing the score to screen and a new
    //           button for the user to return to main menu
    public ResultsMenu(MainFrame parent, Game game, BackgroundMusic nullMusic) {
        super(parent, "data/images/gamemenubackground.jpg", "", 100, nullMusic);
        this.game = game;

        text = new JTextPane();
        initText(text, 40);

        JButton continueButton = new JButton(" Continue ");
        JPanel buttonPanel = initButtonPanel(continueButton, new Color(62, 176, 125));

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        initAudio();

        setSize(800, 600);
        add(spacing, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        repaint();
        slowPrint(game.getHome().getName() + ": " + game.getHomeScore() + "\n\n"
                + game.getAway().getName() + ": " + game.getAwayScore(), 200);
    }

    // EFFECTS:  initializes menu audio; plays win.wav if
    //           user team won, and loss.wav if they lost
    public void initAudio() {
        Clip clip;
        try {
            AudioInputStream audioInput;
            if (game.getDidHomeTeamWin()) {
                audioInput = AudioSystem.getAudioInputStream(new File("data/audio/win.wav"));
            } else {
                audioInput = AudioSystem.getAudioInputStream(new File("data/audio/loss.wav"));
            }
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // EFFECTS:  handles user input to continue button, if the
    //           user won, then prompts them to name a new recruit
    //           and add that pet to their team, otherwise motivate
    //           them to win the next game
    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getDidHomeTeamWin()) {
            String playerMessage = "Congrats on the win! You have a new recruit\n"
                    + "to your team. What would you like to name them?";
            String playerName = JOptionPane.showInputDialog(playerMessage, null);
            Player player = new Player(playerName, game.getHome().getWins());
            player.generateImagePath();
            parent.addPlayerToTeam(player);
            parent.mainMenu();
        } else {
            String message = "Your team is sad about the loss...\n"
                    + "But are filled with determination to do better next time!";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
            parent.mainMenu();
        }
    }
}
