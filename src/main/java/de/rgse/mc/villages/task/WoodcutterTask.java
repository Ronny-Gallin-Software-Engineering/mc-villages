package de.rgse.mc.villages.task;

import com.google.common.collect.ImmutableMap;
import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

import java.util.Optional;

public class WoodcutterTask extends Task<LumberjackEntity> {

    private static final int RUN_TIME = 300;
    private long lastCheckedTime;

    public WoodcutterTask() {
        super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED));
    }

    protected boolean shouldRun(ServerWorld serverWorld, LumberjackEntity entity) {
        if (serverWorld.getTime() - this.lastCheckedTime < RUN_TIME) {
            return false;
        } else if (serverWorld.random.nextInt(2) != 0) {
            return false;
        } else {
            this.lastCheckedTime = serverWorld.getTime();
            GlobalPos globalPos = entity.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).get();
            return globalPos.getDimension() == serverWorld.getRegistryKey() && globalPos.getPos().isWithinDistance(entity.getPos(), 1.73D);
        }
    }

    @Override
    protected void run(ServerWorld serverWorld, LumberjackEntity entity, long l) {
        Brain<LumberjackEntity> brain = (Brain<LumberjackEntity>) entity.getBrain();
        brain.remember(MemoryModuleType.LAST_WORKED_AT_POI, l);
        brain.getOptionalMemory(MemoryModuleType.JOB_SITE).ifPresent((globalPos) -> {
            brain.remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(globalPos.getPos()));
        });
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, LumberjackEntity entity, long l) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
        if (!optional.isPresent()) {
            return false;
        } else {
            GlobalPos globalPos = optional.get();
            return globalPos.getDimension() == serverWorld.getRegistryKey() && globalPos.getPos().isWithinDistance(entity.getPos(), 1.73D);
        }
    }
}
