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

    public Creature newCatFlower(CreatureFactory creatureFactory) {
        Creature catflower = new Creature(world, 'C', AsciiPanel.yellow);
        world.addAtEmptyLocation(catflower);
        new CatflowerAi(catflower, creatureFactory);
        return catflower;
    }

    public Creature newCatflower(CreatureFactory creatureFactory, int boundX1, int boundX2, int boundY1, int boundY2) {
        Creature catflower = new Creature(world, 'C', AsciiPanel.yellow);
        world.addAtEmptyLocation(catflower, boundX1, boundX2, boundY1, boundY2);
        new CatflowerAi(catflower, creatureFactory);
        return catflower;
    }


    public static void setAsPlayer(Creature creature) {
        new PlayerAi(creature);
    }
}