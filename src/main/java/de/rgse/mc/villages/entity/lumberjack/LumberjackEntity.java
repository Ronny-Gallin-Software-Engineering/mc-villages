package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.animation.VillagesAnimations;
import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.goal.*;
import de.rgse.mc.villages.sensor.VillagesSensors;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.Arrays;

public class LumberjackEntity extends ToolUserEntity implements IAnimatable {

    public LumberjackEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        initBrain(world);
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        setSettlerData(getSettlerData().withProfession(VillagesProfessions.LUMBERJACK));
        setSettlerData(getSettlerData().withProfession(VillagesProfessions.LUMBERJACK));

        ItemStack axe = new ItemStack(Items.WOODEN_AXE, 1);
        mainTool.addStack(axe);

        return initialize;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(2, new MoveToTreeGoal(this));
        this.goalSelector.add(2, CollectItemsGoal.ofTags(this, Arrays.asList(ItemTags.SAPLINGS, ItemTags.LOGS)));
        this.goalSelector.add(3, new PlantSaplingGoal(this));
        this.goalSelector.add(3, new DeliverItemsToStorageGoal(this, ItemTags.SAPLINGS));
        this.goalSelector.add(3, new DeliverItemsToStorageGoal(this, ItemTags.LOGS));
        this.goalSelector.add(4, new BreakTreeGoal(this));
    }

    @Override
    public void loot(ItemEntity item) {
        super.loot(item);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return super.canPickupItem(stack) || stack.isIn(ItemTags.SAPLINGS) || stack.isIn(ItemTags.LOGS);
    }

    @Override
    protected PlayState handleAnimation(AnimationEvent<SettlerEntity> event) {
        AnimationController<SettlerEntity> controller = event.getController();

        if (getRunningGoals().contains(IdentifierUtil.goal(BreakTreeGoal.class)) && !event.isMoving()) {
            controller.setAnimation(VillagesAnimations.CHOP_TREE);
            return PlayState.CONTINUE;
        } else {
            return super.handleAnimation(event);
        }

    }

    static {
        sensors.addAll(Arrays.asList(VillagesSensors.TREE_SENSOR, VillagesSensors.SAPLING_SENSOR));
        memories.addAll(Arrays.asList(VillagesMemories.TREE, VillagesMemories.SAPLING));
    }
}
