package de.rgse.mc.villages.task.lumberjack;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.task.MoveToRememberedPosTask;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;

public class CollectSaplingTask extends MoveToRememberedPosTask<LumberjackEntity> {

    public CollectSaplingTask(SettlerData settlerData) {
        super(Map.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, VillagesMemories.SAPLING, MemoryModuleState.VALUE_PRESENT), VillagesMemories.SAPLING, settlerData, 2, 300);
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, LumberjackEntity entity) {
        return super.shouldRun(serverWorld, entity);
    }

    @Override
    protected void run(ServerWorld serverWorld, LumberjackEntity entity, long time) {
        VillagesNetwork.notifyStartTask(entity, IdentifierUtil.task(this));
        super.run(serverWorld, entity, time);
    }

    @Override
    protected void finishRunning(ServerWorld world, LumberjackEntity entity, long time) {
        VillagesNetwork.notifyStopTask(entity, IdentifierUtil.task(this));
        super.finishRunning(world, entity, time);
        entity.getBrain().forget(VillagesMemories.SAPLING);
    }
}
