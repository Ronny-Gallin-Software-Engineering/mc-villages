package de.rgse.mc.villages.particle;

import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;

public interface Applyable {

    ParticleEffect getEffect();

    default boolean isApplyable(Entity settler) {
        return true;
    }
}
