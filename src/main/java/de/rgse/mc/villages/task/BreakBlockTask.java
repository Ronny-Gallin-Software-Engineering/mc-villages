package de.rgse.mc.villages.task;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.skill.Skill;
import de.rgse.mc.villages.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BreakBlockTask extends TargetTask<Block> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final MemoryModuleType<GlobalPos> memory;
    private final Identifier skill;

    private int counter;

    protected BreakBlockTask(MemoryModuleType<GlobalPos> memory, Identifier skill, Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, int runtime) {
        super(requiredMemoryState, runtime);
        this.memory = memory;
        this.skill = skill;
    }

    protected BreakBlockTask(MemoryModuleType<GlobalPos> memory, Identifier skill, int runtime) {
        super(Map.of(memory, MemoryModuleState.REGISTERED), runtime);
        this.memory = memory;
        this.skill = skill;
    }

    protected BreakBlockTask(MemoryModuleType<GlobalPos> memory, Identifier skill) {
        this(memory, skill, 1000);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, ToolUserEntity entity) {
        Optional<GlobalPos> rememberedPosition = entity.getBrain().getOptionalMemory(memory);
        if (rememberedPosition.isPresent() && rememberedPosition.get().getDimension() == entity.world.getRegistryKey()) {
            BlockState targetBlockState = entity.world.getBlockState(rememberedPosition.get().getPos());

            if (isTarget(targetBlockState.getBlock())) {
                return canMine(entity, rememberedPosition.get().getPos()) && isAtBlock(entity, rememberedPosition.get().getPos());

            } else {
                entity.getBrain().forget(memory);
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    protected void run(ServerWorld world, ToolUserEntity entity, long time) {
        LOGGER.info("{} start", entity.getSettlerData().getVillagerName());
        this.counter = 1;
    }

    @Override
    protected void finishRunning(ServerWorld world, ToolUserEntity entity, long time) {
        LOGGER.info("{} stop", entity.getSettlerData().getVillagerName());
    }

    @Override
    protected void keepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(memory);

        if (optionalMemory.isPresent()) {
            BlockPos pos = optionalMemory.get().getPos();

            BlockState targetBlockState = world.getBlockState(pos);
            entity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(pos.getX(), pos.getZ(), pos.getZ()));

            if (this.counter > 0 && !world.isClient && this.counter % 20 == 0) {
                onBlockHit(world, entity, targetBlockState);
            }

            if (this.counter % getBreakDuration(entity) == 0) {
                breakBlock(world, entity, pos);
            }

            ++this.counter;
        }
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        Optional<GlobalPos> rememberedPosition = entity.getBrain().getOptionalMemory(memory);
        return rememberedPosition.isPresent() && isAtBlock(entity, rememberedPosition.get().getPos()) && isTarget(world.getBlockState(rememberedPosition.get().getPos()).getBlock());
    }

    protected int getBreakDuration(ToolUserEntity entity) {
        return 60;
    }

    protected void onBlockHit(ServerWorld world, ToolUserEntity entity, BlockState blockState) {
    }

    public void breakBlock(ServerWorld world, ToolUserEntity entity, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);

        ItemStack mainHandStack = entity.getMainHandStack();
        LootContext.Builder builder = new LootContext.Builder(world);
        builder.parameter(LootContextParameters.TOOL, mainHandStack);
        builder.parameter(LootContextParameters.ORIGIN, entity.getPos());

        Optional<Skill> workSkill = entity.getSettlerData().getSkills().getSkill(skill);
        workSkill.ifPresent(s -> {
            BigDecimal level = BigDecimal.valueOf(s.getLevel());
            builder.luck(level.divide(BigDecimal.valueOf(5), RoundingMode.UP).floatValue());
        });

        mainHandStack.damage(1, entity, s -> s.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY));

        List<ItemStack> droppedStacks = blockState.getDroppedStacks(builder);
        ItemUtil.markAsVillagesStack(droppedStacks, entity);

        for (ItemStack stack : droppedStacks) {
            ItemStack remains = entity.getInventory().addStack(stack);

            if (!remains.isEmpty()) {
                ItemEntity itemEntity = new ItemEntity(entity.world, pos.getX(), pos.getY(), pos.getZ(), remains);
                world.spawnEntity(itemEntity);
            }

        }

        world.removeBlock(pos, false);
        this.onDestroyBlock(world, entity, pos, blockState);
    }

    public void onDestroyBlock(ServerWorld world, ToolUserEntity entity, BlockPos pos, BlockState blockState) {
    }

    private boolean isAtBlock(ToolUserEntity entity, BlockPos blockPos) {
        return blockPos.isWithinDistance(entity.getBlockPos(), 2);
    }

    protected boolean canMine(ToolUserEntity entity, BlockPos targetPos) {
        boolean result = false;
        ItemStack mainTool = entity.getMainTool();
        if (mainTool.getItem() instanceof MiningToolItem miningTool) {
            BlockState blockState = entity.world.getBlockState(targetPos);
            result = miningTool.isSuitableFor(blockState) && miningTool.getMaterial().getMiningSpeedMultiplier() > 1f;
        }

        return result;
    }
}
