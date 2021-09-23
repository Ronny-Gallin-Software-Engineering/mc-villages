package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesMemories;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.*;
import java.util.function.Predicate;

public class POISensor extends Sensor<SettlerEntity> {

    private int cooldown = 0;

    private final PointOfInterestType targetPoi;

    public POISensor(PointOfInterestType targetPoi) {
        this.targetPoi = targetPoi;
    }

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        if (++cooldown > 50) {
            cooldown = 0;
            List<PointOfInterest> inCircle = world
                    .getPointOfInterestStorage().getInCircle(poi -> poi.equals(targetPoi), entity.getBlockPos(), (int) entity.getSettlerData().getViewDistance(), PointOfInterestStorage.OccupationStatus.HAS_SPACE)
                    .filter(this::isValidate)
                    .toList();

            Brain<?> brain = entity.getBrain();
            Optional<Map<PointOfInterestType, List<PointOfInterest>>> optionalMemory = brain.getOptionalMemory(VillagesMemories.POI);
            Map<PointOfInterestType, List<PointOfInterest>> pois = optionalMemory.orElse(new HashMap<>());

            pois.put(targetPoi, inCircle);

            brain.remember(VillagesMemories.POI, pois);
        }
    }

    protected boolean isValidate(PointOfInterest poi) {
        return true;
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesMemories.POI);
    }

}
