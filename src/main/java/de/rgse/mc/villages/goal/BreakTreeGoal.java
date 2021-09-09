package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.pattern.TreePattern;
import de.rgse.mc.villages.skill.VillagesSkills;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.BlockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BreakTreeGoal extends BreakBlockGoal {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final List<Item> AXES = Arrays.asList(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE);

    public BreakTreeGoal(LumberjackEntity lumberjack) {
        super(lumberjack, VillagesMemories.TREE, VillagesSkills.LUMBERJACK);
        targetTags = Collections.singleton(BlockTags.LOGS);
    }

    @Override
    public void start() {
        LOGGER.info("{} start", getToolUser().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(getToolUser(), IdentifierUtil.goal(this));
        super.start();
        SimpleInventory inventory = getToolUser().getMainTool();

        for (int i = 0; i <= inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (AXES.contains(stack.getItem())) {
                getToolUser().equipStack(EquipmentSlot.MAINHAND, stack);
            }
        }
    }

    @Override
    protected void onBlockHit(ServerWorld world, BlockState blockState) {
        super.onBlockHit(world, blockState);
        world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, targetPos.getY() + 0.7D, targetPos.getZ() + 0.5D, 3, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
        world.playSound(null, targetPos, SoundEvents.BLOCK_WOOD_HIT, SoundCategory.BLOCKS, .8f, .5f);
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", getToolUser().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(getToolUser(), IdentifierUtil.goal(this));
        super.stop();
        getToolUser().equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    protected int getBreakDuration() {
        int treeSize = TreePattern.getTreeSize(targetPos, getToolUser().world);
        return treeSize * super.getBreakDuration();
    }

    @Override
    public void onDestroyBlock(ServerWorld serverWorld, BlockPos pos, BlockState blockState) {
        for (int i = 0; i < 20; ++i) {
            double e = serverWorld.getRandom().nextGaussian() * 0.02D;
            double f = serverWorld.getRandom().nextGaussian() * 0.02D;
            double g = serverWorld.getRandom().nextGaussian() * 0.02D;
            serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, (double) targetPos.getY(), targetPos.getZ() + 0.5D, 1, e, f, g, 0.15000000596046448D);
        }
        serverWorld.playSound(null, targetPos, SoundEvents.BLOCK_WOOD_BREAK, SoundCategory.BLOCKS, .8f, .5f);
        BlockPos nextLevelLog = TreePattern.getNextLevelLog(pos, blockState.getBlock(), serverWorld);
        if(nextLevelLog != null) {
            breakBlock(nextLevelLog);
        }
    }
}
