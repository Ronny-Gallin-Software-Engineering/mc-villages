package de.rgse.mc.villages.task.settler;

import de.rgse.mc.villages.entity.VillageEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesMemories;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class FindVillageTask extends Task<SettlerEntity> {

    public FindVillageTask() {
        super(Map.of(VillagesMemories.VILLAGE, MemoryModuleState.REGISTERED));
    }

    @Override
    public boolean shouldRun(ServerWorld world, SettlerEntity entity) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(VillagesMemories.VILLAGE);

        if (optionalMemory.isPresent() && world.getBlockEntity(optionalMemory.get().getPos()) instanceof VillageEntity village) {
            return !village.isFounded();
        }

        return true;
    }

    @Override
    public void run(ServerWorld world, SettlerEntity entity, long time) {
        entity.getBrain().forget(VillagesMemories.VILLAGE);

        ServerWorld serverWorld = (ServerWorld) entity.world;
        Optional<BlockPos> nearestPosition = serverWorld.getPointOfInterestStorage()
                .getInCircle(poi -> poi.equals(PointOfInterestType.LIBRARIAN), entity.getBlockPos(), 500, PointOfInterestStorage.OccupationStatus.ANY)
                .filter(poi -> {
                    BlockEntity blockEntity = world.getBlockEntity(poi.getPos());
                    return blockEntity instanceof VillageEntity village && village.isFounded();
                }).map(PointOfInterest::getPos).min(Comparator.comparingDouble(blockPos2 -> blockPos2.getSquaredDistance(entity.getBlockPos())));

        nearestPosition.ifPresent(poi -> entity.getBrain().remember(VillagesMemories.VILLAGE, GlobalPos.create(entity.world.getRegistryKey(), poi)));
    }
}
