package de.rgse.mc.villages.task;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class HelloTask extends Task<LivingEntity> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int COOLDOWN = 500;
    private int ticksWaited = 0;

    private int ticksRan;

    public HelloTask() {
        super(Map.of(VillagesModuleMemoryTypes.SAY_HELLO, MemoryModuleState.VALUE_PRESENT));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        return ++ticksWaited >= COOLDOWN;
    }

    @Override
    protected void run(ServerWorld serverWorld, LivingEntity wanderer, long l) {
        LOGGER.info("say hello from {}", wanderer.getUuidAsString());
    }

    @Override
    protected void finishRunning(ServerWorld serverWorld, LivingEntity villagerEntity, long l) {
        villagerEntity.getBrain().forget(MemoryModuleType.LOOK_TARGET);
        villagerEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        this.ticksRan = 0;
        this.ticksWaited = 0;
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, LivingEntity villagerEntity, long l) {
        ++this.ticksRan;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, LivingEntity villagerEntity, long l) {
        return this.ticksRan < 1;
    }
}
