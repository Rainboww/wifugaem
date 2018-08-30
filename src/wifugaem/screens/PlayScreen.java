package wifugaem.screens;

import asciiPanel.AsciiPanel;
import wifugaem.*;

import java.awt.event.KeyEvent;

public class PlayScreen implements Screen {

    private World world;
    private Creature player;
    private int screenWidth;
    private int screenHeight;


    public PlayScreen(MapType maptype) {
        screenWidth = 80;
        screenHeight = 21;
        CreatureFactory creatureFactory = null;

        switch (maptype) {
            case CAVES:
                createCaveWorld();
                creatureFactory = new CreatureFactory(world);
                createCatflower(creatureFactory);
                break;
            case FIELD:
                createFieldWorld();
                break;
            case CAVE_BUILDINGS:
                createCaveBuildingsWorld();
                break;
            case FIELD_BUILDINGS:
                createFieldBuildingsWorld();
                break;
        }

        if (creatureFactory == null) {
            creatureFactory = new CreatureFactory(world);
        }
        player = creatureFactory.newPlayer();
    }

    private void createCatflower(CreatureFactory creatureFactory) {
        for (int i = 0; i < 8; i++) {
            creatureFactory.newCatFlower(creatureFactory);
        }
    }

    private void createFungus(CreatureFactory creatureFactory) {
        for (int i = 0; i < 8; i++) {
            creatureFactory.newFungus(creatureFactory);
        }
    }

    public PlayScreen(World world, Creature player) {
        screenWidth = 80;
        screenHeight = 21;
        this.world = world;
        this.player = player;
    }

    private void createCaveWorld() {
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .build();
    }

    private void createCaveBuildingsWorld() {
        world = new WorldBuilder(90, 31)
                .makeCaves()
                .addBuildings()
                .build();
    }

    private void createFieldWorld() {
        world = new WorldBuilder(90, 31)
                .makeFields()
                .build();
    }

    private void createFieldBuildingsWorld() {
        world = new WorldBuilder(90, 31)
                .makeFields()
                .addBuildings()
                .build();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++) {
            for (int y = 0; y < screenHeight; y++) {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        //terminal.write('X', player.x - left, player.y - top);


        for (Creature creature : world.creatures) {
            if (left <= creature.x && creature.x < left + screenWidth && top <= creature.y && creature.y < top + screenHeight) {
                terminal.write(creature.glyph(), creature.x - left, creature.y - top, creature.color());
            }
        }
    }


    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H:
                player.moveBy(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L:
                player.moveBy(1, 0);
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K:
                player.moveBy(0, -1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J:
                player.moveBy(0, 1);
                break;
            case KeyEvent.VK_Y:
                player.moveBy(-1, -1);
                break;
            case KeyEvent.VK_U:
                player.moveBy(1, -1);
                break;
            case KeyEvent.VK_B:
                player.moveBy(-1, 1);
                break;
            case KeyEvent.VK_N:
                player.moveBy(1, 1);
                break;
            case KeyEvent.VK_S:
                WifuGameSerializer.serializeToPlayer(player);
                break;
        }

        world.update();

        return this;
    }
}

enum MapType {
    CAVES,
    CAVE_BUILDINGS,
    FIELD,
    FIELD_BUILDINGS;
}