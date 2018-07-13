package wifugaem;


import asciiPanel.AsciiPanel;

import java.awt.*;

public enum Tile {
    FLOOR((char) 250, AsciiPanel.yellow),
    WALL((char) 177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    BUILDING_WALL('=', AsciiPanel.yellow),
    BUILDING_FLOOR((char) 250, AsciiPanel.yellow),
    TREE('$', AsciiPanel.green),
    GRASS((char) 250, AsciiPanel.green),
    DOOR_CLOSED('|', AsciiPanel.yellow),
    DOOR_OPEN('/', AsciiPanel.yellow);

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }

    public boolean canOpen() {
        return this == Tile.DOOR_CLOSED;
    }

    //prolly need to make this an actual property or smth
    public boolean isGround() {
        return this != WALL && this != BOUNDS && this != BUILDING_WALL && this != TREE && this != DOOR_CLOSED;
    }
}
