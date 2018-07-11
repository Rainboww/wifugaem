package wifugaem.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import wifugaem.Creature;
import wifugaem.PlayerAi;
import wifugaem.WifuGameSerializer;
import wifugaem.World;

public class StartScreen implements Screen{
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("rl tutorial", 1, 1);
        terminal.writeCenter("-- press [enter] to start --", 21);
        terminal.writeCenter("-- press [spacebar] to load saved world if it exists--", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_ENTER: return new PlayScreen();
            case KeyEvent.VK_SPACE: Creature p = WifuGameSerializer.deserializeFromPlayer();
                                    if (!(p == null)) {
                                        World w = p.getWorld();
                                        new PlayerAi(p);
                                        return new PlayScreen(w,p);
                                    } else return new PlayScreen();
            case KeyEvent.VK_PAGE_UP: return new MapEditScreen();
            case KeyEvent.VK_PAGE_DOWN: Creature q = WifuGameSerializer.deserializeFromPlayer();
                                        if (!(q == null)) {
                                            World x = q.getWorld();
                                            new DebugAi(q);
                                            return new MapEditScreen(x,q);
                                        } else return new MapEditScreen();
            default: return this;
        }
    }
}
