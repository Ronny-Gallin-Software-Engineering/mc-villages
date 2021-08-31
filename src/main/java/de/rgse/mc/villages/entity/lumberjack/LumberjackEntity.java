package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

public class LumberjackEntity extends SettlerEntity implements IAnimatable {

    public LumberjackEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;

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
        this.goalSelector.add(2, new WanderAroundGoal(this, 0.4D, 60));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }
}
