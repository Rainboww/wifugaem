package wifugaem;

import asciiPanel.AsciiPanel;

import javax.swing.*;

public class AppMain extends JFrame {

    private AsciiPanel terminal;

    public AppMain(){
        super();
        terminal = new AsciiPanel();
        terminal.write("tutorial", 1, 1);
        add(terminal);
        pack();
    }

    public static void main(String[] args) {
        AppMain app = new AppMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
