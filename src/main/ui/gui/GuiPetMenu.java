package ui.gui;

import model.Player;
import model.PlayerTeam;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.ToIntFunction;

import static javax.swing.BorderFactory.createEmptyBorder;

// Application pet menu that displays the collection of
// players (pets) on the user's team as a list of buttons
public class GuiPetMenu extends Menu {
    private JButton returnButton;    // button to return to the main menu
    private JButton speedButton;     // button to return total team speed
    private JButton defenseButton;   // button to return total team defense
    private JButton offenseButton;   // button to return total team offense
    private JButton moraleButton;    // button to return total team morale
    private JPanel topPanel;         // panel to display team name
    private JPanel bottomPanel;      // panel to house return button
    private JScrollPane scrollPane;  // to add scrollable functionality to list of players
    private List<Player> players;    // list of players on user's team

    // EFFECTS:  constructs a new pet menu with a background
    //           image and music, and a list of scrollable buttons,
    //           each representing a different pet on user's team
    public GuiPetMenu(MainFrame parent, BackgroundMusic backgroundMusic) {
        super(parent, "data/images/petmenubackground.jpg",
                "data/audio/trainingmenu.wav", 10, backgroundMusic);

        initTopPanel();
        initCenterPanel();
        initBottomPanel();

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        repaint();
        parent.pack();
        parent.setSize(800, 600);
    }

    // MODIFIES: this
    // EFFECTS:  initializes the top panel of the menu, displaying
    //           the name of the user's team
    private void initTopPanel() {
        topPanel = new JPanel();
        String teamName = parent.getTeam().getName();
        JLabel teamTitle = new JLabel();
        teamTitle.setText(teamName);
        teamTitle.setForeground(Color.WHITE);
        teamTitle.setFont(new Font("SansSerif", Font.BOLD, 30));
        topPanel.add(teamTitle);
        topPanel.setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS:  initializes the center panel of the menu, generating
    //           a named button for each pet on user's team and adding
    //           them to a scrollable panel
    private void initCenterPanel() {
        JPanel centerPanel = new JPanel();
        scrollPane = new JScrollPane(centerPanel);
        centerPanel.setLayout(new GridLayout(0, 3, 20, 20));
        players = parent.getTeam().getPlayers();
        for (Player player : players) {
            JButton button = new JButton(player.getName());
            button.setName(String.valueOf(player));
            initButton(button, new Color(36, 159, 67), 225, 80, 20);
            centerPanel.add(button);
        }
        centerPanel.setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(createEmptyBorder());
        scrollPane.getViewport().setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS:  initializes the bottom panel of the menu,
    //           housing the return and "Get Total Team X" buttons
    private void initBottomPanel() {
        Color buttonColor = new Color(36, 159, 67);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        speedButton = new JButton("<html>Get Total<br />Team Speed<html>");
        initButton(speedButton, buttonColor, 140, 60, 15);

        defenseButton = new JButton("<html>Get Total<br />Team Offense<html>");
        initButton(defenseButton, buttonColor, 140, 60, 15);

        offenseButton = new JButton("<html>Get Total<br />Team Defense<html>");
        initButton(offenseButton, buttonColor, 140, 60, 15);

        moraleButton = new JButton("<html>Get Total<br />Team Morale<html>");
        initButton(moraleButton, buttonColor, 140, 60, 15);

        returnButton = new JButton("<html>Return to<br />Main Menu<html>");
        initButton(returnButton, buttonColor, 140, 60, 15);

        bottomPanel.add(speedButton);
        bottomPanel.add(defenseButton);
        bottomPanel.add(offenseButton);
        bottomPanel.add(moraleButton);
        bottomPanel.add(returnButton);
        bottomPanel.setOpaque(false);
    }

    // EFFECTS:  handles user input to any button in the menu
    //           ==================
    //           for return button:
    //              open main menu through parent
    //           for speed button:
    //              open a popup displaying the teams total speed
    //           for defense button:
    //              open a popup displaying the teams total defense
    //           for offense button:
    //              open a popup displaying the teams total offense
    //           for morale button:
    //              open a popup displaying the teams total morale
    //           for any player button:
    //              find the player associated with button and
    //              open a new training menu set to them
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(returnButton)) {
            backgroundMusic.endLoop();
            parent.mainMenu();
        } else if (e.getSource().equals(speedButton)) {
            displayTotalTeamStat(PlayerTeam::getTeamSpeed, "speed");
        } else if (e.getSource().equals(defenseButton)) {
            displayTotalTeamStat(PlayerTeam::getTeamDefense, "defense");
        } else if (e.getSource().equals(offenseButton)) {
            displayTotalTeamStat(PlayerTeam::getTeamOffense, "offense");
        } else if (e.getSource().equals(moraleButton)) {
            displayTotalTeamStat(PlayerTeam::getTeamMorale, "morale");
        } else {
            JButton source = (JButton) e.getSource();
            for (Player player : players) {
                if (source.getName().equals(String.valueOf(player))) {
                    parent.trainingMenu(player, backgroundMusic);
                }
            }
        }
    }

    // REQUIRES: getter is a method reference of the form:
    //              PlayerTeam::getTeamX
    //           where X is a field of Player
    // EFFECTS:  displays a popup to the screen with the
    //           total team stat specified by the getter
    public void displayTotalTeamStat(ToIntFunction<PlayerTeam> getter, String stat) {
        int total = getter.applyAsInt(parent.getTeam());
        String message = "Total team " + stat + " is: " + total;
        JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
    }
}
