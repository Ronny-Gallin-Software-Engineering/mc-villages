package de.rgse.mc.villages.util;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.particle.VillagesParticleEffects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.StringUtils;
import software.bernie.geckolib3.core.controller.AnimationController;

import java.util.Random;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesParticleUtil {

    private static final Random RANDOM = new Random();

    public static AnimationController.IParticleListener<SettlerEntity> createParticleListener(Entity entity, int particleCount, Supplier<Float> velocitySupplier, int deviation) {

        return event -> {
            ParticleEffect effect = VillagesParticleEffects.getEffect(event.effect);
            int count = particleCount;

            if (null != effect && (StringUtils.isBlank(event.script) || ExpressionUtil.instance().evaluateEntity(entity, event.script))) {
                Vec3d up = entity.getPos().add(0, 2, 0);

                while (count-- > 0) {
                    float vx = velocitySupplier.get();
                    float vy = velocitySupplier.get();
                    float vz = velocitySupplier.get();

                    double ox = RANDOM.nextInt(deviation) * .1f;
                    double oy = RANDOM.nextInt(deviation) * .1f;
                    double oz = RANDOM.nextInt(deviation) * .1f;

                    ox = RANDOM.nextBoolean() ? ox * -1 : ox;
                    oy = RANDOM.nextBoolean() ? oy * -1 : oy;
                    oz = RANDOM.nextBoolean() ? oz * -1 : oz;

                    entity.getEntityWorld().addParticle(VillagesParticleEffects.GRUPY_PARTICLE_EFFECT, ox + up.getX(), oy + up.getY(), oz + up.getZ(), vx, vy, vz);
                }
            }
        };
    }
}
