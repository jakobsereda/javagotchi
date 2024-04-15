package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Main menu of the application, including a background
// image and music, and functionality to access all major
// areas of the game
public class GuiMainMenu extends Menu {
    private JButton petButton;    // button to open the pet management menu
    private JButton gameButton;   // button to play a game against a random enemy team
    private JButton saveButton;   // button to save current game to file
    private JButton quitButton;   // button to quit the application

    // EFFECTS:  constructs a new main menu with a background
    //           image, music, and buttons to accept user input
    public GuiMainMenu(MainFrame parent) {
        super(parent, "data/images/mainmenubackground.jpg",
                "data/audio/mainmenu.wav", 250, null);

        JPanel buttonPanel = initButtons();

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        setSize(800, 600);
        add(spacing, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        repaint();
    }

    // MODIFIES: this
    // EFFECTS:  initializes buttons and places them into a container,
    //           which is returned
    private JPanel initButtons() {
        Color buttonColor = new Color(236, 32, 37);

        petButton = new JButton(" Open Pets Menu ");
        initButton(petButton, buttonColor, 300, 80, 20);

        gameButton = new JButton(" Play GotchiBall Game ");
        initButton(gameButton, buttonColor, 300, 80, 20);

        saveButton = new JButton(" Save Game to File ");
        initButton(saveButton, buttonColor, 300, 80, 20);

        quitButton = new JButton(" Quit Game ");
        initButton(quitButton, buttonColor, 300, 80, 20);

        return initButtonPanel();
    }

    // EFFECTS:  constructs the container that houses the buttons
    //           in the main menu
    public JPanel initButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        JPanel firstButtonRow = new JPanel();
        firstButtonRow.setOpaque(false);

        JPanel secondButtonRow = new JPanel();
        secondButtonRow.setOpaque(false);

        FlowLayout buttonManager = new FlowLayout();
        buttonManager.setHgap(40);

        firstButtonRow.setLayout(buttonManager);
        secondButtonRow.setLayout(buttonManager);
        firstButtonRow.add(petButton);
        firstButtonRow.add(gameButton);
        secondButtonRow.add(saveButton);
        secondButtonRow.add(quitButton);
        buttonPanel.add(firstButtonRow);
        buttonPanel.add(secondButtonRow);
        return buttonPanel;
    }

    // EFFECTS:  handles user input to any button in the menu
    //           ===============
    //           for pet button:
    //              open a new pet menu through parent
    //           for game button:
    //              open a new game menu through parent
    //           for save button:
    //              save the game to file through parent
    //           for quit button:
    //              close and quit the application
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(petButton)) {
            backgroundMusic.endLoop();
            parent.petMenu();
        } else if (e.getSource().equals(gameButton)) {
            backgroundMusic.endLoop();
            parent.gameMenu();
        } else if (e.getSource().equals(saveButton)) {
            parent.savePlayerTeam();
        } else if (e.getSource().equals(quitButton)) {
            backgroundMusic.endLoop();
            parent.close();
        }
    }
}
