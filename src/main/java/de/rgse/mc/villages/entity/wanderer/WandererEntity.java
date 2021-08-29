package de.rgse.mc.villages.entity.wanderer;

import com.google.common.collect.ImmutableList;
import de.rgse.mc.villages.goal.MoveToCampfireGoal;
import de.rgse.mc.villages.task.HelloTask;
import de.rgse.mc.villages.task.VillagesActivities;
import de.rgse.mc.villages.task.VillagesModuleMemoryTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
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

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class WandererEntity extends PassiveEntity {

    private BlockPos wanderTarget;
    private boolean settled;

    public WandererEntity(EntityType<WandererEntity> entityType, World world) {
        super(entityType, world);
        initBrain(world);
    }

    private void initBrain(World world) {
        brain.remember(VillagesModuleMemoryTypes.SAY_HELLO, true);

        brain.setTaskList(VillagesActivities.GREET, 0, ImmutableList.of(new HelloTask()));
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
