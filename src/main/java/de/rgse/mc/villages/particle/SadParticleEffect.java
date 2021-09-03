package de.rgse.mc.villages.particle;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class SadParticleEffect extends DefaultParticleType implements Applyable {

    protected SadParticleEffect() {
        super(true);
    }

    @Override
    public DefaultParticleType getType() {
        return ParticleTypes.SMOKE;
    }

    @Override
    public boolean isApplyable(Entity entity) {
        return SettlerEntity.class.isAssignableFrom(entity.getClass()) && ((SettlerEntity) entity).getSettlerData().getMood().isSad();
    }

    @Override
    public ParticleEffect getEffect() {
        return this;
    }
}
