package wifugaem;

import asciiPanel.AsciiPanel;

import java.awt.*;

public enum TileFilling {
    DIRT_WALL((char) 177, AsciiPanel.yellow, false),
    BUILDING_WALL('=', AsciiPanel.yellow, false),
    TREE('$', AsciiPanel.green, false),
    DOOR_CLOSED('|', AsciiPanel.yellow, false),
    DOOR_OPEN('/', AsciiPanel.yellow, true),
    EMPTY(' ', AsciiPanel.yellow, true);


    private char glyph;
    private Color color;
    private boolean isPassable;

    TileFilling(char glyph, Color color, boolean passable) {
        this.glyph = glyph;
        this.color = color;
        this.isPassable = passable;
    }

    protected char getGlyph() {
        return glyph;
    }

    protected Color getColor() {
        return color;
    }

    protected boolean isDiggable() {
        return this == TileFilling.DIRT_WALL;
    }

    protected boolean canOpen() {
        return this == TileFilling.DOOR_CLOSED;
    }

    protected boolean canEnter() {
        return this.isPassable;
    }
}