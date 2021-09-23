package de.rgse.mc.villages.task.settler;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.ClockUtil;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

import java.util.Map;
import java.util.Optional;

public class BreakfastTask extends Task<SettlerEntity> {

    public BreakfastTask() {
        super(Map.of(VillagesMemories.EATEN, MemoryModuleState.REGISTERED));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, SettlerEntity entity) {
        Optional<Long> remembersEaten = entity.getBrain().getOptionalMemory(VillagesMemories.EATEN);

        return !remembersEaten.isPresent() || world.getTime() - remembersEaten.get() >= ClockUtil.TICKS_PER_DAY.longValue();
    }

    @Override
    protected void keepRunning(ServerWorld world, SettlerEntity entity, long time) {
        super.keepRunning(world, entity, time);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, SettlerEntity entity, long time) {
        return super.shouldKeepRunning(world, entity, time);
    }

    @Override
    protected void run(ServerWorld world, SettlerEntity entity, long time) {
        super.run(world, entity, time);
        entity.getBrain().remember(VillagesMemories.EATEN, time);
    }

    @Override
    protected void finishRunning(ServerWorld world, SettlerEntity entity, long time) {
        super.finishRunning(world, entity, time);
    }


}
