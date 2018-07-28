package wifugaem;

public class FungusAi extends CreatureAi {
    private CreatureFactory factory;
    private int spreadcount;

    public FungusAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
    }

    public FungusAi(Creature creature, CreatureFactory factory, int spreadcount) {
        super(creature);
        this.factory = factory;
        this.spreadcount = spreadcount;
    }

    public void onUpdate() {
        if (spreadcount < 7 && Math.random() < 0.01) {
            spread();
        }
    }
    private void spread(){
        int x = creature.x + (int)(Math.random() * 11) - 5;
        int y = creature.y + (int)(Math.random() * 11) - 5;

        if (!creature.canEnter(x, y))
            return;

        Creature child = factory.newFungus(factory, ++spreadcount);
        child.x = x;
        child.y = y;

    }
}

