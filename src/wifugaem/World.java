package wifugaem;

import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

public class World implements Serializable {

    private Tile[][] tiles;
    private int width;

    public List<Creature> creatures;

    public int width() {
        return width;
    }

    private int height;

    public int height() {
        return height;
    }

    public World(Tile[][] tiles) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<Creature>();
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return new Tile(TileBase.BOUNDS);
        else
            return tiles[x][y];
    }

    public boolean tileExists(int x, int y) {
        return (x >= 0 && x < width && y >= 0 & y < height);
    }


    public char glyph(int x, int y) {
        return tile(x, y).getGlyph();
    }

    public Color color(int x, int y) {
        return tile(x, y).getColor();
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable())
            tiles[x][y].setFilling(TileFilling.EMPTY);
    }

    public void open(int x, int y) {
        if (tile(x, y).canOpen())
            switch (tile(x, y).getFilling()) {
                case DOOR_CLOSED:
                    tiles[x][y].setFilling(TileFilling.DOOR_OPEN);
                    break;
            }
    }

    public void addAtEmptyLocation(Creature creature) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        while (!tile(x, y).canEnter() || creature(x, y) != null);

        creature.x = x;
        creature.y = y;
        creatures.add(creature);
    }

    public void addAtEmptyLocation(Creature creature, int boundX1, int boundX2, int boundY1, int boundY2) {
        int x;
        int y;
        boolean fail = true;
        //test if valid location exists
        //TODO: check only valid locations
        for (int a = boundX1; a <= boundX2; a++) {
            for (int b = boundY1; b <= boundY2; b++) {
                if (tile(a, b).canEnter() && creature(a, b) == null) {
                    fail = false;
                }
            }
        }
        //boundaries inclusive
        if (!fail) {
            do {
                x = (int) (Math.random() * (boundX2 - boundX1 + 1)) + boundX1;
                y = (int) (Math.random() * (boundY2 - boundY1 + 1)) + boundY1;
            }
            while (!tile(x, y).canEnter() || creature(x, y) != null);
        creature.x = x;
        creature.y = y;
        creatures.add(creature);
        } else {
            //TODO: failure condition
        }
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Creature creature(int x, int y) {
        for (Creature c : creatures) {
            if (c.x == x && c.y == y)
                return c;
        }
        return null;
    }

    public void remove(Creature other) {
        creatures.remove(other);
    }

    public void update() {
        List<Creature> toUpdate = new ArrayList<Creature>(creatures);
        for (Creature creature : toUpdate) {
            creature.update();
        }
    }
}