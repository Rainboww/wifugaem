package wifugaem;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
    private World world;

    public CreatureFactory(World world) {
        this.world = world;
    }

    public Creature newPlayer() {
        Creature player = new Creature(world, '@', AsciiPanel.brightWhite);
        world.addAtEmptyLocation(player);
        new PlayerAi(player);
        return player;
    }

    public Creature newFungus(CreatureFactory creaturefactory) {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus, creaturefactory);
        return fungus;
    }

    public Creature newFungus(CreatureFactory creaturefactory, int spreadcount) {
        Creature fungus = new Creature(world, 'f', AsciiPanel.green);
        world.addAtEmptyLocation(fungus);
        new FungusAi(fungus, creaturefactory, spreadcount);
        return fungus;
    }


    public static void setAsPlayer(Creature creature) {
        new PlayerAi(creature);
    }
}
