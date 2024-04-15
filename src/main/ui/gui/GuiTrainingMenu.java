package ui.gui;

import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// Application training menu that displays the sprite
// of a single Player (pet) on the user's team, and offers
// the functionality to train and motivate them before game
public class GuiTrainingMenu extends Menu {
    private final Player player;            // player shown by menu
    private final SpritePanel spritePane;   // for animations of player default sprites
    private ImageIcon training;             // training stat sprite
    private ImageIcon boosting;             // boosting morale sprite
    private JPanel bottomPanel;             // bottom panel to house buttons
    private JButton speedButton;            // button for training speed
    private JButton offenseButton;          // button for training offense
    private JButton defenseButton;          // button for training defense
    private JButton moraleButton;           // button for boosting morale
    private JButton returnButton;           // button to return to pets menu

    // EFFECTS:  constructs a new training menu with a
    //           background image and music, and given player's
    //           sprite with buttons for training given player
    public GuiTrainingMenu(MainFrame parent, Player player, BackgroundMusic backgroundMusic) {
        super(parent, "data/images/trainingmenubackground.jpg", "", 0, backgroundMusic);
        this.player = player;

        training = new ImageIcon(player.getImagePath() + "/training.png");
        boosting = new ImageIcon(player.getImagePath() + "/boosting.png");
        training = resize(training, 480, 640);
        boosting = resize(boosting, 480, 640);

        spritePane = new SpritePanel(player);
        initBottomPanel();

        setSize(800, 600);
        add(spritePane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        repaint();
        parent.pack();
        parent.setSize(800, 600);
    }

    // MODIFIES: this
    // EFFECTS:  initializes the bottom panel of the menu, which
    //           displays the buttons to the screen
    public void initBottomPanel() {
        Color buttonColor = new Color(36, 159, 67);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        speedButton = new JButton("<html> Train Speed <br />level: " + player.getSpeed() + "</html>");
        initButton(speedButton, buttonColor, 140, 60, 15);

        defenseButton = new JButton("<html> Train Defense <br />level: " + player.getDefense() + "</html>");
        initButton(defenseButton, buttonColor, 140, 60, 15);

        offenseButton = new JButton("<html> Train Offense <br />level: " + player.getOffense() + "</html>");
        initButton(offenseButton, buttonColor, 140, 60, 15);

        moraleButton = new JButton("<html> Boost Morale <br />level: " + player.getMorale() + "</html>");
        initButton(moraleButton, buttonColor, 140, 60, 15);

        returnButton = new JButton(" Back to Pets ");
        initButton(returnButton, buttonColor, 140, 60, 15);

        bottomPanel.add(speedButton);
        bottomPanel.add(defenseButton);
        bottomPanel.add(offenseButton);
        bottomPanel.add(moraleButton);
        bottomPanel.add(returnButton);
        bottomPanel.setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS:  updates all button titles based on the current
    //           stat of player
    public void updateButtonTitles() {
        speedButton.setText("<html> Train Speed <br />level: " + player.getSpeed() + "</html>");
        defenseButton.setText("<html> Train Defense <br />level: " + player.getDefense() + "</html>");
        offenseButton.setText("<html> Train Offense <br />level: " + player.getOffense() + "</html>");
        moraleButton.setText("<html> Boost Morale <br />level: " + player.getMorale() + "</html>");
    }

    // MODIFIES: this
    // EFFECTS:  displays the given sprite to the screen for 500
    //           milliseconds before returning to previous state
    public void displaySprite(ImageIcon sprite) {
        removeAll();
        JLabel label = new JLabel(sprite, JLabel.CENTER);
        label.setPreferredSize(new Dimension(470, 470));
        add(label, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
        Timer timer = new Timer(500, e -> {
            removeAll();
            add(spritePane, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    // EFFECTS:  resizes the given image icon to provided
    //           width and height
    public static ImageIcon resize(ImageIcon imageIcon, int newWidth, int newHeight) {
        Image temp = imageIcon.getImage();
        Image resized = temp.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resized);
    }

    // EFFECTS:  handles user input to any button in the menu
    //           =================
    //           for speed button:
    //              display training sprite and train speed
    //           for defense button:
    //              display training sprite and train defense
    //           for offense button:
    //              display training sprite and train offense
    //           for morale button:
    //              display boosting sprite and boost morale
    //           for return button:
    //              return to pets menu
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(speedButton)) {
            player.trainSpeed();
            trainingAction(training);
        } else if (e.getSource().equals(defenseButton)) {
            player.trainDefense();
            trainingAction(training);
        } else if (e.getSource().equals(offenseButton)) {
            player.trainOffense();
            trainingAction(training);
        } else if (e.getSource().equals(moraleButton)) {
            player.boostMorale();
            trainingAction(boosting);
        } else if (e.getSource().equals(returnButton)) {
            parent.reopenPetMenu(backgroundMusic);
        }
    }

    // MODIFIES: this
    // EFFECTS:  if player is out of energy, displays pop up
    //           window informing the user, else updates button
    //           titles and displays the given image to the screen
    private void trainingAction(ImageIcon image) {
        if (player.getEnergy() == 0) {
            String message = "Out of energy!";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
        } else {
            updateButtonTitles();
            displaySprite(image);
        }
    }
}
