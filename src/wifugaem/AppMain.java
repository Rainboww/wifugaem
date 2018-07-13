package wifugaem;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Properties;
import java.util.Random;

import wifugaem.screens.Screen;
import wifugaem.screens.StartScreen;

public class AppMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public AppMain() {
        super();
        terminal = new AsciiPanel();
        add(terminal);
        pack();
        screen = new StartScreen();
        addKeyListener(this);
        repaint();
    }

    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
        WifuGameSerializer.makeFolders();
    }
}
