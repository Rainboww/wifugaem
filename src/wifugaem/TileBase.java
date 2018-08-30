package wifugaem;


import asciiPanel.AsciiPanel;

import java.awt.*;

public enum TileBase {
    GROUND((char) 250, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack),
    BUILDING_FLOOR((char) 250, AsciiPanel.yellow),
    GRASS((char) 250, AsciiPanel.green);

    private char glyph;
    private Color color;

    TileBase(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }

    protected char getGlyph() {
        return glyph;
    }

    protected Color getColor() {
        return color;
    }

    protected boolean canEnter() {
        return this != BOUNDS;
    }
}
