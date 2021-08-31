package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.animation.VillagesAnimationRegistry;
import de.rgse.mc.villages.entity.EntityName;
import de.rgse.mc.villages.entity.Gender;
import de.rgse.mc.villages.entity.VillagesEntityRegistry;
import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.text.NameText;
import de.rgse.mc.villages.util.IdentifierUtil;
import de.rgse.mc.villages.util.NameUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
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

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SettlerEntity extends PassiveEntity implements IAnimatable {

    private static final TrackedData<SettlerData> SETTLER_DATA = DataTracker.registerData(SettlerEntity.class, SettlerData.SETTLER_DATA);

    private final AnimationFactory animationFactory = new AnimationFactory(this);

    public SettlerEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.setSettlerData(this.getSettlerData());
        setCustomNameVisible(true);
    }

    public SettlerData getSettlerData() {
        return this.dataTracker.get(SETTLER_DATA);
    }

    public void setSettlerData(SettlerData settlerData) {
        this.dataTracker.set(SETTLER_DATA, settlerData);
    }

    @Nullable
    @Override
    public SettlerEntity createChild(ServerWorld world, PassiveEntity parent) {
        if (parent.getClass().isAssignableFrom(SettlerEntity.class)) {
            return new SettlerEntity(VillagesEntityRegistry.SETTLER, world);
        } else {
            return null;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<SettlerEntity> controller = new AnimationController(this, "controller", 0, this::handleAnimation);
        animationData.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
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
            controller.setAnimation(VillagesAnimationRegistry.SETTLER_WALK);
        } else if (controller.getCurrentAnimation() == null && world.getRandom().nextInt(20) == 0) {
            controller.setAnimation(VillagesAnimationRegistry.randomSettlerIdle());

        } else {
            controller.setAnimation(VillagesAnimationRegistry.SETTLER_IDLE_0);
        }

        return PlayState.CONTINUE;
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SETTLER_DATA, new SettlerData(Gender.MALE, null, VillagesProfessionRegistry.NONE));
    }

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData initialize = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);

        Gender gender = Gender.random(world.toServerWorld());
        EntityName name = NameUtil.instance().createName(gender);

        this.setSettlerData(this.getSettlerData().withProfession(VillagesProfessionRegistry.NONE).withGender(gender).withName(name));

        if (spawnReason == SpawnReason.BREEDING) {
            this.setSettlerData(this.getSettlerData().withProfession(VillagesProfessionRegistry.NONE));
        }

        setCustomName(NameText.of(this));

        return initialize;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        NbtCompound villageNbt = new NbtCompound();

        SettlerData settlerData = getSettlerData();

        if (settlerData.getGender() != null) {
            villageNbt.putString(IdentifierUtil.createString("gender"), settlerData.getGender().name());
        }

        if (settlerData.getVillagerName() != null) {
            villageNbt.putString(IdentifierUtil.createString("name"), settlerData.getVillagerName().toString());
        }

        if (settlerData.getProfession() != null) {
            villageNbt.putString(IdentifierUtil.createString("profession"), settlerData.getProfession().getIdentifier().toString());
        }

        nbt.put(VillagesMod.MOD_ID, villageNbt);
    }


    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains(VillagesMod.MOD_ID)) {
            SettlerData settlerData = getSettlerData();

            NbtCompound villagesNbt = nbt.getCompound(VillagesMod.MOD_ID);

            if (villagesNbt.contains(IdentifierUtil.createString("gender"))) {
                settlerData.setGender(Gender.valueOf(villagesNbt.getString(IdentifierUtil.createString("gender"))));
            }

            if (villagesNbt.contains(IdentifierUtil.createString("name"))) {
                settlerData.setVillagerName(EntityName.parse(villagesNbt.getString(IdentifierUtil.createString("name"))));
            }

            if (villagesNbt.contains(IdentifierUtil.createString("profession"))) {
                Identifier identifier = Identifier.tryParse(villagesNbt.getString(IdentifierUtil.createString("profession")));
                settlerData.setProfession(VillagesProfessionRegistry.of(identifier));
            }
        }

        setCustomName(NameText.of(this));
        setCustomNameVisible(true);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isSleeping()) {
            if (this.isBaby()) {
                return ActionResult.success(this.world.isClient);
            } else {
                String env = player.world.isClient() ? "client" : "server";
                VillagesMod.LOGGER.info("dialog between {} and {} on {}", player.getEntityName(), getEntityName(), env);
                return ActionResult.success(this.world.isClient);
            }
        } else {
            return super.interactMob(player, hand);
        }
    }
}
