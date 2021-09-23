package de.rgse.mc.villages.task;

import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Map;
import java.util.Optional;

public class MoveToRememberedPosTask<E extends SettlerEntity> extends Task<E> {

    protected final MemoryModuleType<GlobalPos> memory;
    private final int reach;
    protected long nextUpdateTime;
    protected final float maxDistance;
    protected final float walkSpeed;

    public MoveToRememberedPosTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, MemoryModuleType<GlobalPos> memory, SettlerData settlerData, int reach, int runtime) {
        super(requiredMemoryState, runtime);
        this.memory = memory;
        this.walkSpeed = settlerData.getDefaultMovementSpeed();
        this.maxDistance = settlerData.getViewDistance();
        this.reach = reach;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, E entity) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(this.memory);
        return optional.isPresent() && serverWorld.getRegistryKey() == optional.get().getDimension() && optional.get().getPos().isWithinDistance(entity.getPos(), this.maxDistance);
    }

    @Override
    protected void run(ServerWorld serverWorld, E entity, long time) {
        if (time > this.nextUpdateTime) {
            Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(memory);
            if (optionalMemory.isPresent()) {
                GlobalPos globalPos = optionalMemory.get();
                Vec3d vec3d = Vec3d.of(globalPos.getPos());
                vec3d = vec3d.subtract(Vec3d.ofCenter(entity.getMovementDirection().getVector()));
                entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.walkSpeed, reach));
                this.nextUpdateTime = time + 180L;
            }
        }

    }

    @Override
    protected void keepRunning(ServerWorld world, E entity, long time) {
        Optional<WalkTarget> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);
        if (optionalMemory.isPresent()) {
            BlockPos targetPos = optionalMemory.get().getLookTarget().getBlockPos();
            entity.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), walkSpeed);
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, E entity, long time) {
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, E entity, long time) {
        Optional<WalkTarget> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);
        return optional.isPresent() && !hasReached(entity);
    }

    protected boolean hasReached(SettlerEntity entity) {
        Optional<WalkTarget> optionalMemory = entity.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);

        if (optionalMemory.isPresent()) {
            BlockPos pos = optionalMemory.get().getLookTarget().getBlockPos();
            return pos.isWithinDistance(entity.getBlockPos(), reach);
        }

        return false;
    }
}
