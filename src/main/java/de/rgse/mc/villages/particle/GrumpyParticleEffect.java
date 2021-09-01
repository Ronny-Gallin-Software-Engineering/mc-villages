package de.rgse.mc.villages.particle;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

public class GrumpyParticleEffect extends DefaultParticleType {

    protected GrumpyParticleEffect() {
        super(true);
    }

    @Override
    public DefaultParticleType getType() {
        return ParticleTypes.SMOKE;
    }
}
