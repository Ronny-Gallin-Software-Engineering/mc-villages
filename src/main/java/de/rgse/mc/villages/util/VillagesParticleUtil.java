package de.rgse.mc.villages.util;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.particle.Applyable;
import de.rgse.mc.villages.particle.ItemParticleEffect;
import de.rgse.mc.villages.particle.VillagesParticleEffects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib3.core.controller.AnimationController;

import java.util.Random;
import java.util.function.Supplier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesParticleUtil {

    private static final Random RANDOM = new Random();

    public static AnimationController.IParticleListener<SettlerEntity> createParticleListener(Entity entity, int particleCount, Supplier<Float> velocitySupplier, int deviation) {

        return event -> {
            String[] split = event.effect.split(",");

            for (String effectName : split) {
                Applyable effect = VillagesParticleEffects.getEffect(effectName);

                if (null != effect && effect.isApplyable(entity)) {
                    VillagesParticleUtil.spawnSadParticle(entity, particleCount, velocitySupplier, deviation);
                }
            }
        };
    }

    public static final void spawnSadParticle(Entity entity, int particleCount, Supplier<Float> velocitySupplier, int deviation) {
        int count = particleCount;
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

            entity.getEntityWorld().addParticle(VillagesParticleEffects.SAD_PARTICLE_EFFECT.getEffect(), ox + up.getX(), oy + up.getY(), oz + up.getZ(), vx, vy, vz);
        }
    }

    public static void spawnItemParticle(ServerWorld world, SettlerEntity entity, Item required) {
        Vec3d pos = entity.getPos().add(0d, 2.5d, 0d);
        world.spawnParticles(new ItemParticleEffect(required), pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0, 0, 0);
    }
}
