package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.animation.VillagesAnimations;
import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.entity.settler.SettlerTaskListFactory;
import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.skill.Skill;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.task.lumberjack.ChopTreeTask;
import de.rgse.mc.villages.task.lumberjack.PlantSaplingTask;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
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

import java.util.LinkedList;
import java.util.List;

import static de.rgse.mc.villages.sensor.VillagesSensors.*;

public class LumberjackEntity extends ToolUserEntity implements IAnimatable {

    private static final List<SensorType<? extends Sensor<? super SettlerEntity>>> sensors = new LinkedList<>(List.of(TREE_SENSOR, SAPLING_SENSOR, CRAFTING_TABLE_SENSOR));
    private static final List<MemoryModuleType<?>> memories = new LinkedList<>(List.of(VillagesMemories.TREE, VillagesMemories.POI));

    public LumberjackEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        initBrain(world);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return Brain.createProfile(LumberjackEntity.memories, LumberjackEntity.sensors);
    }

    @Override
    public boolean isRequiredTool(Item item) {
        return item instanceof AxeItem;
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        setSettlerData(getSettlerData().withProfession(VillagesProfessions.LUMBERJACK).withSkill(new Skill(VillagesProfessions.LUMBERJACK)));

        ItemStack axe = new ItemStack(Items.WOODEN_AXE, 1);
        setMainTool(axe);

        return initialize;
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
        return super.canPickupItem(stack) || stack.isIn(ItemTags.SAPLINGS) || stack.isIn(ItemTags.LOGS) || isRequiredTool(stack.getItem());
    }

    @Override
    protected PlayState handleAnimation(AnimationEvent<SettlerEntity> event) {
        AnimationController<SettlerEntity> controller = event.getController();

        if (getRunningGoals().contains(IdentifierUtil.ofClass(ChopTreeTask.class)) && !event.isMoving()) {
            controller.setAnimation(VillagesAnimations.LUMBERJACK_CHOP_TREE);
            return PlayState.CONTINUE;
        } else if (getRunningGoals().contains(IdentifierUtil.ofClass(PlantSaplingTask.class)) && !event.isMoving()) {
            controller.setAnimation(VillagesAnimations.LUMBERJACK_PLANT_SAPLING);
            return PlayState.CONTINUE;
        } else {
            return super.handleAnimation(event);
        }
    }

    @Override
    protected void initBrain(World world) {
        Brain<LumberjackEntity> b = (Brain<LumberjackEntity>) brain;

        b.setTaskList(Activity.WORK, LumberjackTaskListFactory.createWorkTasks(this));
        b.setTaskList(Activity.IDLE, SettlerTaskListFactory.createIdleTasks());

        b.setCoreActivities(SettlerEntity.coreActivities);
        b.setSchedule(LumberjackTaskListFactory.SCHEDULE);
        b.refreshActivities(world.getTimeOfDay(), world.getTime());
    }

    static {
        LumberjackEntity.sensors.addAll(SettlerEntity.sensors);
        LumberjackEntity.memories.addAll(SettlerEntity.memories);
    }
}
