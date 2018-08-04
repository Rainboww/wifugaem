package wifugaem;

import java.awt.*;
import java.io.Serializable;

public class Tile implements Serializable {
    private TileBase base;
    private TileFilling filling;
    private double elevation;

    private char glyph;
    private java.awt.Color color;

    public Tile(TileBase b, TileFilling f, double elevation) {
        base = b;
        filling = f;
        this.elevation = elevation;
        updateTile();
    }

    public Tile(TileBase b, TileFilling f) {
        base = b;
        filling = f;
        this.elevation = 0;
        updateTile();
    }

    public Tile(TileBase b, double elevation) {
        base = b;
        filling = TileFilling.EMPTY;
        this.elevation = elevation;
        updateTile();
    }

    public Tile(TileBase b) {
        base = b;
        filling = TileFilling.EMPTY;
        this.elevation = 0;
        updateTile();
    }

    public Tile updateTile() {
        if (filling == TileFilling.EMPTY) {
            glyph = base.getGlyph();
            color = base.getColor();
        } else {
            glyph = filling.getGlyph();
            color = filling.getColor();
        }
        return this;
    }

    public char getGlyph() {
        return glyph;
    }

    public Color getColor() {
        return color;
    }

    public double getElevation() {
        return elevation;
    }

    public TileFilling getFilling() {
        return filling;
    }

    public TileBase getBase() {
        return base;
    }

    public boolean isDiggable() {
        return filling == TileFilling.EMPTY || filling.isDiggable();
    }

    public boolean canOpen() {
        return filling == TileFilling.EMPTY || filling.canOpen();
    }

    public boolean isEmpty() {
        return filling == TileFilling.EMPTY;
    }

    public boolean canEnter() {
        if (filling == TileFilling.EMPTY) {
            return base.canEnter();
        } else return filling.canEnter();
    }

    public Tile setFilling(TileFilling f) {
        filling = f;
        updateTile();
        return this;
    }
}
