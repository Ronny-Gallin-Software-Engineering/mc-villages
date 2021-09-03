package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.goal.MoveToTreeGoal;
import de.rgse.mc.villages.sensor.VillagesSensorRegistry;
import de.rgse.mc.villages.task.VillagesModuleMemoryTypeRegistry;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

import java.util.List;
import java.util.Optional;

public class LumberjackEntity extends SettlerEntity implements IAnimatable {

    public LumberjackEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

        initBrain(world);
    }

    protected void initBrain(World world) {
        brain.remember(VillagesModuleMemoryTypeRegistry.KNOW_WOOD, Optional.empty());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        setSettlerData(getSettlerData().withProfession(VillagesProfessionRegistry.LUMBERJACK));
        return initialize;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MoveToTreeGoal(this));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return Brain.createProfile(List.of(VillagesModuleMemoryTypeRegistry.KNOW_WOOD), List.of(VillagesSensorRegistry.TREE_SENSOR));
    }
}
