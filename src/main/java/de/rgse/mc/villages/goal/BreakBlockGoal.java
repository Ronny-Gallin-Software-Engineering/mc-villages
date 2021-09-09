package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.skill.Skill;
import de.rgse.mc.villages.util.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public abstract class BreakBlockGoal extends TargetGoal<Block> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ToolUserEntity toolUser;
    private final MemoryModuleType<BlockPos> memory;
    private final Identifier skill;

    protected BlockPos targetPos;

    private int counter;

    protected BreakBlockGoal(ToolUserEntity toolUser, MemoryModuleType<BlockPos> memory, Identifier skill) {
        this.toolUser = toolUser;
        this.memory = memory;
        this.skill = skill;
    }

    public ToolUserEntity getToolUser() {
        return toolUser;
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> rememberedPosition = this.toolUser.getBrain().getOptionalMemory(memory);
        if (rememberedPosition.isPresent()) {
            BlockState targetBlockState = toolUser.world.getBlockState(rememberedPosition.get());

            if (isTarget(targetBlockState.getBlock())) {
                targetPos = rememberedPosition.get();
                return isAtBlock();

            } else {
                toolUser.getBrain().forget(memory);
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public void start() {
        LOGGER.info("{} start", toolUser.getSettlerData().getVillagerName());
        super.start();
        this.counter = 1;
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", toolUser.getSettlerData().getVillagerName());
    }

    public void tick() {
        super.tick();
        World world = this.toolUser.world;
        BlockState targetBlockState = world.getBlockState(targetPos);
        toolUser.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, new Vec3d(targetPos.getX(), targetPos.getZ(), targetPos.getZ()));

        if (this.counter > 0 && !world.isClient && this.counter % 20 == 0) {
            onBlockHit(((ServerWorld) world), targetBlockState);
        }

        if (this.counter % getBreakDuration() == 0) {
            breakBlock(targetPos);
        }

        ++this.counter;
    }

    @Override
    public boolean shouldContinue() {
        return isTarget(toolUser.world.getBlockState(targetPos).getBlock());
    }

    protected int getBreakDuration() {
        return 60;
    }

    protected void onBlockHit(ServerWorld world, BlockState blockState) {
    }

    public void breakBlock(BlockPos pos) {
        World world = toolUser.world;
        BlockState blockState = world.getBlockState(pos);

        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld) world;
            ItemStack mainHandStack = toolUser.getMainHandStack();
            LootContext.Builder builder = new LootContext.Builder(serverWorld);
            builder.parameter(LootContextParameters.TOOL, mainHandStack);
            builder.parameter(LootContextParameters.ORIGIN, toolUser.getPos());

            Optional<Skill> workSkill = toolUser.getSettlerData().getSkills().getSkill(skill);
            workSkill.ifPresent(s -> {
                BigDecimal level = BigDecimal.valueOf(s.getLevel());
                builder.luck(level.divide(BigDecimal.valueOf(5), RoundingMode.UP).floatValue());
            });

            mainHandStack.damage(1, getToolUser(), s -> {
                s.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
            });

            List<ItemStack> droppedStacks = blockState.getDroppedStacks(builder);

            for (ItemStack stack : droppedStacks) {
                ItemStack remains = toolUser.getInventory().addStack(stack);

                if (!remains.isEmpty()) {
                    ItemEntity itemEntity = new ItemEntity(toolUser.world, pos.getX(), pos.getY(), pos.getZ(), remains);
                    serverWorld.spawnEntity(itemEntity);
                }
            }
        }

        world.removeBlock(pos, false);

        if (!world.isClient) {
            this.onDestroyBlock((ServerWorld) world, pos, blockState);
        }
    }

    public void onDestroyBlock(ServerWorld world, BlockPos pos, BlockState blockState) {
    }

    private boolean isAtBlock() {
        List<BlockPos> surroundingBlocks = BlockUtil.getSurroundingBlocks(targetPos);
        surroundingBlocks.add(targetPos);
        BlockPos playerPos = toolUser.getBlockPos();

        return surroundingBlocks.stream().anyMatch(sb -> {
            int deltaX = sb.getX() - playerPos.getX();
            if (deltaX >= -1 && deltaX <= 1) {
                int deltaZ = sb.getZ() - playerPos.getZ();
                return deltaZ >= -1 && deltaZ <= 1;
            }
            return false;
        });
    }
}
