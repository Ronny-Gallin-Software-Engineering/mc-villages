package de.rgse.mc.villages.particle;

import de.rgse.mc.villages.animation.VillagesEffects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesParticleEffects {

    public static Applyable SAD_PARTICLE_EFFECT = new SadParticleEffect();
    private static final Map<String, Applyable> effects = new HashMap<>();

    static {
        effects.put(VillagesEffects.SAD_CLOUD, SAD_PARTICLE_EFFECT);
    }

    public static Applyable getEffect(String effect) {
        return effects.get(effect);
    }
}
