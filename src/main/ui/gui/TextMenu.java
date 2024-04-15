package ui.gui;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

// Represents a menu that displays text by slowing printing
// it to the screen
public abstract class TextMenu extends Menu {
    protected Timer timer;      // timer for text printing
    protected JTextPane text;   // text pane of text to be printed
    protected int index = 0;    // index for checking when message printing is over

    // EFFECTS:  constructs a new TextMenu with same parameters
    //           and specifications as superclass Menu
    public TextMenu(MainFrame parent, String imagePath, String musicPath, int gap, BackgroundMusic backgroundMusic) {
        super(parent, imagePath, musicPath, gap, backgroundMusic);
    }

    // MODIFIES: this
    // EFFECTS:  initializes text by centering, resizing, and
    //           coloring it as appropriate
    public void initText(JTextPane text, int fontSize) {
        StyledDocument documentStyle = text.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);
        text.setOpaque(false);
        text.setForeground(Color.WHITE);
        text.setFont(new Font("SansSerif", Font.BOLD, fontSize));
    }

    // MODIFIES: button
    // EFFECTS:  creates and returns a panel with given button of
    //           given color centered on it; this panel and its
    //           associated button have predetermined dimensions to
    //           suit style
    public JPanel initButtonPanel(JButton button, Color buttonColor) {
        initButton(button, buttonColor, 300, 80, 20);

        JPanel spacing = new JPanel();
        spacing.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        FlowLayout buttonManager = new FlowLayout();
        buttonManager.setVgap(20);
        buttonPanel.setLayout(buttonManager);
        buttonPanel.add(button, BorderLayout.CENTER);
        buttonPanel.add(spacing, BorderLayout.CENTER);

        buttonPanel.setOpaque(false);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS:  slowly prints given message one character at a
    //           time to the screen, with the given delay between
    //           each printed character
    // CREDIT:   https://stackoverflow.com/questions/63474574/make-a-typewriter-effect-in-a-jlabel
    public void slowPrint(String message, int delay) {
        if (timer != null && timer.isRunning()) {
            return;
        }
        index = 0;
        text.setText("");

        timer = new Timer(delay, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText(text.getText() + message.charAt(index));
                index++;
                if (index >= message.length()) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    // EFFECTS:  handles user input to buttons
    @Override
    public abstract void actionPerformed(ActionEvent e);
}
