package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.animation.VillagesAnimations;
import de.rgse.mc.villages.entity.*;
import de.rgse.mc.villages.entity.thought.Thoughts;
import de.rgse.mc.villages.gui.SettlerInfoDescription;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.text.NameText;
import de.rgse.mc.villages.util.NameUtil;
import de.rgse.mc.villages.util.NbtUtil;
import de.rgse.mc.villages.util.VillagesParticleUtil;
import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.*;

import static de.rgse.mc.villages.sensor.VillagesSensors.CHEST_SENSOR;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SettlerEntity extends PassiveEntity implements IAnimatable, InventoryOwner, NamedScreenHandlerFactory, PropertyDelegateHolder {

    protected static List<SensorType<? extends Sensor<? super SettlerEntity>>> sensors = new LinkedList<>(List.of(CHEST_SENSOR));
    protected static List<MemoryModuleType<?>> memories = new LinkedList<>(List.of(VillagesMemories.CHEST, VillagesMemories.EATEN, MemoryModuleType.WALK_TARGET, VillagesMemories.VILLAGE));
    protected static Set<Activity> coreActivities = new HashSet<>(Set.of(Activity.CORE));
    private static final TrackedData<SettlerData> SETTLER_DATA = DataTracker.registerData(SettlerEntity.class, SettlerData.SETTLER_DATA);

    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private SimpleInventory inventory = new SimpleInventory(5);
    private List<Identifier> runningGoals = new LinkedList<>();
    private Thoughts thoughts;
    private int syncId;

    public SettlerEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.setSettlerData(this.getSettlerData());
        this.thoughts = new Thoughts(this);
        setCustomNameVisible(true);
        setMovementSpeed(.4f);
        setCanPickUpLoot(true);
        initBrain(world);
    }

    protected void initBrain(World world) {
        Brain<SettlerEntity> b = (Brain<SettlerEntity>) brain;

        b.setTaskList(Activity.IDLE, SettlerTaskListFactory.createIdleTasks());

        b.setCoreActivities(SettlerEntity.coreActivities);
        b.setSchedule(SettlerTaskListFactory.SCHEDULE);
        b.refreshActivities(world.getTimeOfDay(), world.getTime());
    }

    public SettlerData getSettlerData() {
        return this.dataTracker.get(SETTLER_DATA);
    }

    public void setSettlerData(SettlerData settlerData) {
        this.dataTracker.set(SETTLER_DATA, settlerData);
    }

    @Override
    public float getBaseMovementSpeedMultiplier() {
        return super.getBaseMovementSpeedMultiplier() * getSettlerData().getMood().getSpeedModifier();
    }

    @Nullable
    @Override
    public SettlerEntity createChild(ServerWorld world, PassiveEntity parent) {
        if (!isBaby() && parent.getClass().isAssignableFrom(SettlerEntity.class)) {
            return new SettlerEntity(VillagesEntities.SETTLER, world);
        } else {
            return null;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<SettlerEntity> controller = new AnimationController(this, "settler_controller", 0, this::handleAnimation);
        controller.registerParticleListener(VillagesParticleUtil.createParticleListener(this, 2, () -> world.getRandom().nextInt(4) * .01f, 5));
        animationData.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return Brain.createProfile(memories, sensors);
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
    }

    protected PlayState handleAnimation(AnimationEvent<SettlerEntity> event) {
        AnimationController<SettlerEntity> controller = event.getController();

        if (event.isMoving()) {
            controller.setAnimation(event.getAnimatable().getSettlerData().getMood().isSad() ? VillagesAnimations.SETTLER_WALK_SAD : VillagesAnimations.SETTLER_WALK);

        } else if (controller.getCurrentAnimation() == null && world.getRandom().nextInt(20) == 0) {
            controller.setAnimation(VillagesAnimations.randomSettlerIdle());

        } else {
            controller.setAnimation(VillagesAnimations.SETTLER_IDLE_0);
        }

        return PlayState.CONTINUE;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SETTLER_DATA, new SettlerData());
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);

        Gender gender = Gender.random(world.toServerWorld());
        EntityName name = NameUtil.instance().createName(gender);

        long time = world.toServerWorld().getLevelProperties().getTime();

        this.setSettlerData(this.getSettlerData().withProfession(VillagesProfessions.NONE).withGender(gender).withName(name).withBirthday(time).withDefaultMovementSpeed(.4f));

        if (spawnReason == SpawnReason.BREEDING) {
            this.setSettlerData(this.getSettlerData().withProfession(VillagesProfessions.NONE));
        }

        setCustomName(NameText.of(this));

        return initialize;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtUtil.writeSettlerDataToNbt(this, nbt);
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        NbtUtil.readSettlerDataFromNbt(this, nbt);
        initBrain(world);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isSleeping()) {
            if (this.isBaby()) {
                return ActionResult.success(this.world.isClient);
            } else {
                player.openHandledScreen(this);
                return ActionResult.success(this.world.isClient);
            }
        } else {
            return super.interactMob(player, hand);
        }
    }

    @Override
    protected void mobTick() {
        this.getBrain().tick((ServerWorld) this.world, this);
        this.thoughts.tick();
    }

    @Override
    public Text getDisplayName() {
        MutableText text = Objects.requireNonNull(getCustomName()).shallowCopy();

        Profession profession = getSettlerData().getProfession();
        if (profession != VillagesProfessions.NONE) {
            text = text.append(" - ").append(new TranslatableText(getSettlerData().getProfession().getTranslationKey()));
        }

        return text;
    }

    @Override
    public boolean canPickupItem(ItemStack stack) {
        return stack.isFood();
    }

    @Override
    public boolean canPickUpLoot() {
        return !isBaby();
    }

    @Override
    public void loot(ItemEntity item) {
        super.loot(item);
        ItemStack itemStack = item.getStack();
        if (this.canGather(itemStack)) {
            SimpleInventory simpleInventory = this.getInventory();
            boolean bl = simpleInventory.canInsert(itemStack);
            if (!bl) {
                return;
            }

            this.triggerItemPickedUpByEntityCriteria(item);
            this.sendPickup(item, itemStack.getCount());
            ItemStack itemStack2 = simpleInventory.addStack(itemStack);
            if (itemStack2.isEmpty()) {
                item.discard();
            } else {
                itemStack.setCount(itemStack2.getCount());
            }
        }
    }

    @Override
    public Brain<SettlerEntity> getBrain() {
        return (Brain<SettlerEntity>) super.getBrain();
    }

    public SimpleInventory getInventory() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new SettlerInfoDescription(syncId, inv, ScreenHandlerContext.create(world, getBlockPos()));
    }

    @Override
    public PropertyDelegate getPropertyDelegate() {
        ArrayPropertyDelegate arrayPropertyDelegate = new ArrayPropertyDelegate(1);
        arrayPropertyDelegate.set(0, getId());
        return arrayPropertyDelegate;
    }
}
