package wifugaem.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import wifugaem.Creature;
import wifugaem.PlayerAi;
import wifugaem.WifuGameSerializer;
import wifugaem.World;

public class StartScreen implements Screen {
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("rl tutorial", 1, 1);
        terminal.writeCenter("-- press [o - p - l - m] to start --", 21);
        terminal.writeCenter("-- press [spacebar] to load saved world if it exists--", 22);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            //load world
            case KeyEvent.VK_SPACE:
                Creature p = WifuGameSerializer.deserializeFromPlayer();
                if (!(p == null)) {
                    World w = p.getWorld();
                    new PlayerAi(p);
                    return new PlayScreen(w, p);
                } else return this;
                //new editable map (caves)
            case KeyEvent.VK_PAGE_UP:
                return new MapEditScreen();
            //edit saved map
            case KeyEvent.VK_PAGE_DOWN:
                Creature q = WifuGameSerializer.deserializeFromPlayer();
                if (!(q == null)) {
                    World x = q.getWorld();
                    new DebugAi(q);
                    return new MapEditScreen(x, q);
                } else return this;
                //new fields map
            case KeyEvent.VK_O:
                return new PlayScreen(MapType.FIELD);
            //new fields + buildings map
            case KeyEvent.VK_P:
                return new PlayScreen(MapType.FIELD_BUILDINGS);
            //new caves map
            case KeyEvent.VK_L:
                return new PlayScreen(MapType.CAVES);
            //new caves + buildings map
            case KeyEvent.VK_M:
                return new PlayScreen(MapType.CAVE_BUILDINGS);
            default:
                return this;
        }
    }
}
