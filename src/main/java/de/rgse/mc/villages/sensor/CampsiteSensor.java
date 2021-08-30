package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.task.VillagesModuleMemoryTypeRegistry;
import de.rgse.mc.villages.world.VillagesPOITypeRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterestStorage;

import java.util.Optional;
import java.util.Set;

public class CampsiteSensor extends Sensor<LivingEntity> {

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Optional<BlockPos> nearestPosition = world.getPointOfInterestStorage().getNearestPosition(VillagesPOITypeRegistry.CAMPING_SITE.getCompletionCondition(), entity.getBlockPos(), 5, PointOfInterestStorage.OccupationStatus.ANY);
        nearestPosition.ifPresent(bp -> {
            entity.getBrain().remember(VillagesModuleMemoryTypeRegistry.SAY_HELLO, true);
        });
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesModuleMemoryTypeRegistry.SAY_HELLO);
    }
}
