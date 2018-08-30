package wifugaem;

import java.util.Random;

public class CatflowerAi extends CreatureAi {
    private CreatureFactory factory;
    private boolean spread;

    public CatflowerAi(Creature creature, CreatureFactory factory) {
        super(creature);
        this.factory = factory;
        spread = false;
    }

    public void onUpdate() {
        if (!spread) {
            if (Math.random() < 0.01) {
                for (int dx = -2; dx < 3; dx++) {
                    for (int dy = -2; dy < 3; dy++) {
                        if (creature.getWorld().creature(creature.x + dx, creature.y + dy) != null) {
                            if (dx == 0 && dy == 0) {
                                continue;
                            }
                            if (creature.getWorld().creature(creature.x + dx, creature.y + dy).getAi() instanceof CatflowerAi) {
                                int baseX = dx / 2 + creature.x;
                                int baseY = dy / 2 + creature.y;
                                Creature child = factory.newCatflower(factory, baseX - 3, baseX + 3, baseY - 3, baseY + 3);
                                spread = true;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
