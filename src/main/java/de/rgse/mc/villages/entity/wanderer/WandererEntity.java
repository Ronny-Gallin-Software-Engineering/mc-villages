package de.rgse.mc.villages.entity.wanderer;

import de.rgse.mc.villages.goal.MoveToCampfireGoal;
import de.rgse.mc.villages.task.VillagesActivities;
import de.rgse.mc.villages.task.VillagesMemories;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Optional;

public class WandererEntity extends PassiveEntity {

    public WandererEntity(EntityType<WandererEntity> entityType, World world) {
        super(entityType, world);
        initBrain(world);
    }

    public void setWanderTarget(BlockPos wanderTarget) {
        if (wanderTarget != null) {
            brain.remember(VillagesMemories.CAMPSITE, wanderTarget);

        } else {
            brain.forget(VillagesMemories.CAMPSITE);
        }
    }

    public void setSettled(boolean settled) {
        brain.remember(VillagesMemories.SETTLED, settled);
    }

    public BlockPos getWanderTarget() {
        Optional<BlockPos> optionalMemory = brain.getOptionalMemory(VillagesMemories.CAMPSITE);
        return optionalMemory.orElse(null);
    }

    public boolean isSettled() {
        return brain.getOptionalMemory(VillagesMemories.SETTLED).orElse(false);
    }

    private void initBrain(World world) {
        //brain.remember(VillagesModuleMemoryTypeRegistry.SAY_HELLO, true);

        //brain.setTaskList(VillagesActivityRegistry.GREET, 10, ImmutableList.of(new HelloTask()));
        brain.setCoreActivities(Collections.singleton(VillagesActivities.GREET));
        brain.setDefaultActivity(VillagesActivities.GREET);
        brain.resetPossibleActivities();
        brain.refreshActivities(world.getTimeOfDay(), world.getTime());
    }

    @Override
    public Brain<WandererEntity> getBrain() {
        return (Brain<WandererEntity>) super.getBrain();
    }

    @Nullable
    @Override
    public WandererEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

   /* @Override
    protected Brain.Profile<?> createBrainProfile() {
        return Brain.createProfile(List.of(VillagesModuleMemoryTypeRegistry.SAY_HELLO, VillagesModuleMemoryTypeRegistry.CAMPSITE, VillagesModuleMemoryTypeRegistry.SETTLED), List.of(VillagesSensorRegistry.CAMPSITE_SENSOR));
    }*/

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FleeEntityGoal<>(this, ZombieEntity.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, EvokerEntity.class, 12.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, VindicatorEntity.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, VexEntity.class, 8.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, PillagerEntity.class, 15.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, IllusionerEntity.class, 12.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new FleeEntityGoal<>(this, ZoglinEntity.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 0.5D));
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.4D, 60));
        this.goalSelector.add(2, new MoveToCampfireGoal(this));
    }

    @Override
    protected void mobTick() {
        this.getBrain().tick((ServerWorld) this.world, this);
    }
}
