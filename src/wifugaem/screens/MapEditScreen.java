package wifugaem.screens;

import asciiPanel.AsciiPanel;
import wifugaem.*;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MapEditScreen implements Screen{
    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        terminal.write('X', player.x - left, player.y - top);
    }

    public MapEditScreen(){
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
        player.setCreatureAi(new DebugAi(player));
    }

    public MapEditScreen(World world, Creature player) {
        screenWidth = 80;
        screenHeight = 21;
        this.world = world;
        this.player = player;
        this.player.setCreatureAi(new DebugAi(this.player));
    }

    private void createWorld(){
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    public void setTile(int x, int y, World w) {
        Object[] options = Tile.values();
        Object o = (Tile)JOptionPane.showInputDialog(
                null,
                "select tile",
                "tile selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        w.setTile(x,y,(Tile) o);
    }

    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: player.moveBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: player.moveBy( 1, 0); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: player.moveBy( 0,-1); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: player.moveBy( 0, 1); break;
            case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
            case KeyEvent.VK_U: player.moveBy( 1,-1); break;
            case KeyEvent.VK_B: player.moveBy(-1, 1); break;
            case KeyEvent.VK_N: player.moveBy( 1, 1); break;
            case KeyEvent.VK_S: WifuGameSerializer.serializeWorld(world);
                WifuGameSerializer.serializePlayer(player);
                break;
            case KeyEvent.VK_X: setTile(player.x, player.y, world); break;
        }
        return this;
    }
}

class DebugAi extends CreatureAi {
    DebugAi(Creature creature) {
        super(creature);
    }

    public void onEnter(int x, int y, Tile tile) {
            creature.x = x;
            creature.y = y;

    }
}