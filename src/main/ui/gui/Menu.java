package ui.gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a menu JPanel with a parent JFrame, a background
// image and background music
public abstract class Menu extends JPanel implements ActionListener {
    protected MainFrame parent;                   // parent frame of panel
    protected ImageIcon backgroundImage;          // background image of menu
    protected BackgroundMusic backgroundMusic;    // background music of menu

    // EFFECTS:  constructs a new menu with given parent, image,
    //           and music; and specified vertical gap between
    //           elements in layout. if not given existing background
    //           music, creates new instance
    public Menu(MainFrame parent, String imagePath, String musicPath, int gap, BackgroundMusic backgroundMusic) {
        this.parent = parent;
        setLayout(new BorderLayout(0, gap));
        backgroundImage = new ImageIcon(imagePath);
        if (backgroundMusic == null) {
            this.backgroundMusic = new BackgroundMusic(musicPath);
            this.backgroundMusic.loopMusic();
        } else {
            this.backgroundMusic = backgroundMusic;
        }
    }

    // MODIFIES: button
    // EFFECTS:  initializes given button, setting its color, size,
    //           and font based on application style guides and
    //           given parameters
    public void initButton(JButton button, Color buttonColor, int width, int height, int fontSize) {
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(buttonColor);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE));
        button.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        button.addActionListener(this);
    }

    // MODIFIES: this
    // EFFECTS:  paints background image onto the menu
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Dimension d = getSize();
        graphics.drawImage(backgroundImage.getImage(), 0, 0, d.width, d.height, this);
    }

    // EFFECTS:  handles user input to buttons
    @Override
    public abstract void actionPerformed(ActionEvent e);
}
