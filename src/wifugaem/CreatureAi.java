package wifugaem;

import java.io.Serializable;

public class CreatureAi implements Serializable {
    protected Creature creature;

    public CreatureAi(Creature creature) {
        this.creature = creature;
        this.creature.setCreatureAi(this);
    }

    public void onEnter(int x, int y, Tile tile) {
    }


    public void onUpdate() {

    }
}

