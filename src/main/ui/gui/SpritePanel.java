package ui.gui;

import model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

// Manages a sprite panel that alternates between the two default
// sprite images for any given player
// CREDIT: https://stackoverflow.com/questions/13676098/fast-switching-between-two-images
public class SpritePanel extends JPanel {
    private final Player player;       // player to find associated sprites of
    private BufferedImage default1;    // first default sprite of player
    private BufferedImage default2;    // second default sprite of player
    private BufferedImage frame;       // frame which will be animated
    private boolean opened = true;     // boolean for producing animation

    // EFFECTS:  constructs a new sprite panel for associated
    //           player and initializes animation
    public SpritePanel(Player player) {
        this.player = player;
        setOpaque(false);
        initImages();
        Timer timer = new Timer(472, e -> {
            opened = !opened;
            frame = opened ? default1 : default2;
            repaint();
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS:  initializes both default images by reading them
    //           from imagePath of player, then resizing them as
    //           appropriate
    public void initImages() {
        try {
            default1 = ImageIO.read(new File(player.getImagePath() + "/default1.png"));
            default2 = ImageIO.read(new File(player.getImagePath() + "/default2.png"));
        } catch (IOException e) {
            String message = "Error loading sprite images";
            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        default1 = resize(default1, 480, 640);
        default2 = resize(default2, 480, 640);
        frame = default1;
    }

    // EFFECTS:  resizes given buffered image to given width and
    //           height, returning the result
    // CREDIT:   https://stackoverflow.com/questions/9417356/bufferedimage-resize
    public static BufferedImage resize(BufferedImage bufferedImage, int newWidth, int newHeight) {
        Image temp = bufferedImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }

    // EFFECTS:  returns preferred size of sprite panel as a
    //           dimension
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(470, 470);
    }

    // EFFECTS:  paints given graphics to the sprite panel,
    //           centered in the middle of the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (frame != null) {
            int x = (getWidth() - frame.getWidth()) / 2;
            int y = (getHeight() - frame.getHeight()) / 2;
            g2d.drawImage(frame, x, y, this);
        }
        g2d.dispose();
    }
}
