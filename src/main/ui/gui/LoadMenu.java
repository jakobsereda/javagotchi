package ui.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Application load menu, opens on launch and allows user
// to either load a previous save or start a new game
public class LoadMenu extends Menu {
    private JButton loadButton;     // button to load previous save
    private JButton newGameButton;  // button to start a new game

    // EFFECTS:  constructs a new load menu with a background
    //           image, music, and buttons to load a save / start
    //           a new game
    public LoadMenu(MainFrame parent) {
        super(parent, "data/images/loadmenubackground.jpg",
                "data/audio/loadmenu.wav", 200, null);

        JPanel buttonPanel = initButtons();

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        setSize(800, 600);
        add(buttonPanel, BorderLayout.CENTER);
        add(spacing, BorderLayout.NORTH);
        repaint();
    }

    // MODIFIES: this
    // EFFECTS:  initializes buttons and places them into a
    //           container, which is returned
    private JPanel initButtons() {
        Color buttonColor = new Color(62, 176, 125);

        loadButton = new JButton(" Load game from file ");
        initButton(loadButton, buttonColor, 300, 80, 20);

        newGameButton = new JButton(" Play new game ");
        initButton(newGameButton, buttonColor, 300, 80, 20);

        JPanel buttonPanel = new JPanel();
        FlowLayout buttonManager = new FlowLayout();
        buttonManager.setHgap(100);
        buttonPanel.setLayout(buttonManager);
        buttonPanel.add(loadButton);
        buttonPanel.add(newGameButton);
        buttonPanel.setOpaque(false);
        return buttonPanel;
    }

    // EFFECTS:  handles user input to any button in the menu
    //           ================
    //           for load button:
    //              attempt to load a team on file, and open main
    //              menu if successful
    //           for new game button:
    //              open the exposition menu to start a new game
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(loadButton)) {
            boolean result = parent.loadPlayerTeam();
            if (result) {
                backgroundMusic.endLoop();
                parent.mainMenu();
            }
        } else if (e.getSource().equals(newGameButton)) {
            backgroundMusic.endLoop();
            parent.newGame();
        }
    }
}
