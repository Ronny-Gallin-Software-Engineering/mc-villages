package de.rgse.mc.villages.particle;

import de.rgse.mc.villages.animation.VillagesEffectRegistry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.particle.ParticleEffect;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesParticleEffects {

    public static ParticleEffect GRUPY_PARTICLE_EFFECT = new GrumpyParticleEffect();
    private static final Map<String, ParticleEffect> effects = new HashMap<>();

    static {
        effects.put(VillagesEffectRegistry.SAD_CLOUD, GRUPY_PARTICLE_EFFECT);
    }

    public static ParticleEffect getEffect(String effect) {
        return effects.get(effect);
    }
}
