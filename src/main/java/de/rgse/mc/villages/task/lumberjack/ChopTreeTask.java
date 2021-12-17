package de.rgse.mc.villages.task.lumberjack;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.pattern.TreePattern;
import de.rgse.mc.villages.skill.VillagesSkills;
import de.rgse.mc.villages.task.BreakBlockTask;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.IdentifierUtil;
import de.rgse.mc.villages.util.VillagesParticleUtil;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ChopTreeTask extends BreakBlockTask {

    private static final Logger LOGGER = LogManager.getLogger();
    protected final MemoryModuleType<GlobalPos> target;
    private final int reach;
    protected final float maxDistance;
    protected final float walkSpeed;

    public ChopTreeTask(SettlerData settlerData) {
        super(VillagesMemories.TREE, VillagesSkills.LUMBERJACK, Map.of(VillagesMemories.TREE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), 1000);
        targetTags = Collections.singleton(BlockTags.LOGS);
        this.target = VillagesMemories.TREE;
        this.walkSpeed = settlerData.getDefaultMovementSpeed();
        this.maxDistance = settlerData.getViewDistance();
        this.reach = 2;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, ToolUserEntity entity) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(this.target);

        if (optional.isPresent() && canMine(entity, optional.get().getPos())) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(optional.get().getPos(), walkSpeed, reach));
            return serverWorld.getRegistryKey() == optional.get().getDimension() && !hasReached(serverWorld, entity)
                    || super.shouldRun(serverWorld, entity);
        } else if(optional.isEmpty()){
            log.info("unable to chop tree: {}", "no position");
            VillagesParticleUtil.spawnItemParticle(serverWorld, entity, Items.SPRUCE_LOG);

        } else if(canMine(entity, optional.get().getPos())) {
            log.info("unable to chop tree: {}", "no tool");
            ItemStack mainTool = entity.getMainTool();
            VillagesParticleUtil.spawnItemParticle(serverWorld, entity, mainTool.getItem());
        }

        return false;
    }

    @Override
    protected void run(ServerWorld world, ToolUserEntity entity, long time) {
        LOGGER.info("{} start", entity.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(entity, IdentifierUtil.task(this));
        super.run(world, entity, time);
        entity.equipTool();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(target);

        if (optional.isPresent() && canMine(entity, optional.get().getPos())) {
            return !hasReached(world, entity) || super.shouldKeepRunning(world, entity, time);
        } else {
            return false;
        }
    }

    @Override
    protected void keepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(target);
        if (optionalMemory.isPresent()) {
            GlobalPos walkTarget = optionalMemory.get();
            BlockPos targetPos = walkTarget.getPos();

            BlockPos add = entity.getBlockPos().add(entity.getMovementDirection().getVector());
            if(world.getBlockState(add).isIn(BlockTags.LEAVES)) {
                world.breakBlock(add, true, entity);
            }

            add = add.up();
            if(world.getBlockState(add).isIn(BlockTags.LEAVES)) {
                world.breakBlock(add, true, entity);
            }

            if (hasReached(world, entity)) {
                super.keepRunning(world, entity, time);
            } else {
                entity.getNavigation().startMovingTo(targetPos.getX(), targetPos.getY(), targetPos.getZ(), walkSpeed);
            }
        }
    }

    @Override
    protected void onBlockHit(ServerWorld world, ToolUserEntity entity, BlockState blockState) {
        super.onBlockHit(world, entity, blockState);
        BlockPos targetPos = getTargetPos(entity);
        world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, targetPos.getY() + 0.7D, targetPos.getZ() + 0.5D, 3, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
        world.playSound(null, targetPos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, .8f, .5f);
    }

    @Override
    protected void finishRunning(ServerWorld world, ToolUserEntity entity, long time) {
        VillagesNetwork.notifyStopTask(entity, IdentifierUtil.task(this));
        super.finishRunning(world, entity, time);
        entity.unequipTool();
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected int getBreakDuration(ToolUserEntity entity) {
        int treeSize = TreePattern.getTreeSize(getTargetPos(entity), entity.world);
        return treeSize * super.getBreakDuration(entity);
    }

    @Override
    public void onDestroyBlock(ServerWorld serverWorld, ToolUserEntity entity, BlockPos pos, BlockState blockState) {
        BlockPos targetPos = getTargetPos(entity);
        for (int i = 0; i < 20; ++i) {
            double e = serverWorld.getRandom().nextGaussian() * 0.02D;
            double f = serverWorld.getRandom().nextGaussian() * 0.02D;
            double g = serverWorld.getRandom().nextGaussian() * 0.02D;
            serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, (double) targetPos.getY(), targetPos.getZ() + 0.5D, 1, e, f, g, 0.15000000596046448D);
        }
        serverWorld.playSound(null, targetPos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, .8f, .5f);
        BlockPos nextLevelLog = TreePattern.getNextLevelLog(pos, blockState.getBlock(), serverWorld);
        if (nextLevelLog != null) {
            breakBlock(serverWorld, entity, nextLevelLog);
        }
    }

    protected boolean hasReached(ServerWorld world, SettlerEntity entity) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(target);

        if (optionalMemory.isPresent() && world.getRegistryKey() == optionalMemory.get().getDimension()) {
            BlockPos pos = optionalMemory.get().getPos();
            return pos.isWithinDistance(entity.getBlockPos(), reach);
        }

        return false;
    }

    private BlockPos getTargetPos(SettlerEntity entity) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(target);
        return optionalMemory.map(GlobalPos::getPos).orElse(null);
    }
}
