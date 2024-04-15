package ui.gui;

import model.Player;
import model.PlayerTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Application exposition menu that plays at the beginning
// of every new game; provides backstory and prompts user
// for team and first pet name
public class ExpositionMenu extends TextMenu {
    // EFFECTS:  constructs a new exposition menu with a background
    //           image, music, and a continue button, that types the
    //           exposition dialogue to the screen
    public ExpositionMenu(MainFrame parent) {
        super(parent, "data/images/introbackground.jpg",
                "data/audio/exposition.wav", 30, null);

        text = new JTextPane();
        initText(text, 20);

        JButton continueButton = new JButton(" Continue ");
        JPanel buttonPanel = initButtonPanel(continueButton, new Color(62, 176, 125));

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        setSize(800, 600);
        add(spacing, BorderLayout.NORTH);
        add(text, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        repaint();
        slowPrint("Welcome to JavaGotchi!\n\nIn the world of JavaGotchi, all JavaGotchi pets\nlove playing GotchiBall!"
                + " All the best GotchiBall\nteams are mega-famous, and live in mega-mansions.\n\nYou just quit your"
                + " day job to live your dreams\nas a GotchiBall team coach! You and your team of\nJavaGotchi pets"
                + " won't quit until the title of...\n\nGreatest GotchiBall Team Ever!\n\n...is yours.", 100);
    }

    // EFFECTS:  handles user input to the continue button, producing
    //           popup windows prompting the user for a team and
    //           first pet name before opening the main menu
    @Override
    public void actionPerformed(ActionEvent e) {
        String teamMessage = "First, choose a name for your team:";
        String teamName = JOptionPane.showInputDialog(teamMessage, null);
        PlayerTeam team = new PlayerTeam(teamName);
        parent.setTeam(team);

        String playerMessage = "Next, lets recruit your first JavaGotchi pet to join your team!\n"
                + "What would you like to name your first pet?";
        String playerName = JOptionPane.showInputDialog(playerMessage, null);
        Player player = new Player(playerName, 0);
        player.generateImagePath();
        parent.addPlayerToTeam(player);
        backgroundMusic.endLoop();
        parent.mainMenu();
    }
}
