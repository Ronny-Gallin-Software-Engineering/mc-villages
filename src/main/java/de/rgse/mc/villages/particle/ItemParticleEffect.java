package de.rgse.mc.villages.particle;

import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;

public class ItemParticleEffect extends DefaultParticleType {

    private Item item;

    public ItemParticleEffect(Item item) {
        super(true);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public DefaultParticleType getType() {
        return VillagesParticles.ITEM;
    }
}
