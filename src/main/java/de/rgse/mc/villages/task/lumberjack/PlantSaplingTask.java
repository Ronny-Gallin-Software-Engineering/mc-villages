package de.rgse.mc.villages.task.lumberjack;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.skill.Skill;
import de.rgse.mc.villages.skill.VillagesSkills;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class PlantSaplingTask extends Task<LumberjackEntity> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SKILL_THRESHOLD = 500;
    private static final int MIN_COOLDOWN = 200;

    private int indexToPlace = -1;
    private int counter;
    private int cooldown = MIN_COOLDOWN;
    private BlockPos blockPos;

    public PlantSaplingTask(){
        super(Map.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), 1000);
    }

    @Override
    public boolean shouldRun(ServerWorld world, LumberjackEntity entity) {
        if (++cooldown < MIN_COOLDOWN) {
            return false;
        }

        for (int i = 0; i < entity.getInventory().size(); i++) {
            ItemStack stack = entity.getInventory().getStack(i);
            if (ItemTags.SAPLINGS.contains(stack.getItem())) {
                indexToPlace = i;
                break;
            }
        }

        blockPos = entity.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos.down());
        BlockState targetBlockState = world.getBlockState(blockPos);

        return indexToPlace > -1 && (Feature.isSoil(blockState) || blockState.isOf(Blocks.GRASS_BLOCK)) &&
                targetBlockState.isOf(Blocks.AIR) &&
                Feature.isExposedToAir(world::getBlockState, blockPos)
                && Feature.isExposedToAir(world::getBlockState, blockPos.up())
                && Feature.isExposedToAir(world::getBlockState, blockPos.up(2))
                && Feature.isExposedToAir(world::getBlockState, blockPos.up(3));
    }

    @Override
    public void run(ServerWorld world, LumberjackEntity entity, long time) {
        LOGGER.info("{} start", entity.getSettlerData().getVillagerName());
        entity.equipStack(EquipmentSlot.MAINHAND, entity.getInventory().getStack(indexToPlace));
        VillagesNetwork.notifyStartTask(entity, IdentifierUtil.task(this));
        entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(blockPos, 0f, 1));
        cooldown = 0;
        counter = 0;
    }

    @Override
    public void finishRunning(ServerWorld world, LumberjackEntity entity, long time) {
        LOGGER.info("{} stop", entity.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(entity, IdentifierUtil.task(this));
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    @Override
    public boolean shouldKeepRunning(ServerWorld world, LumberjackEntity entity, long time) {
        return indexToPlace >= 0;
    }

    @Override
    public void keepRunning(ServerWorld world, LumberjackEntity entity, long time) {
        Optional<Skill> skill = entity.getSettlerData().getSkills().getSkill(VillagesSkills.LUMBERJACK);
        float level = skill.map(Skill::getLevel).orElse(1f);

        if (counter % 20 == 0) {
            BlockPos targetPos = entity.getBlockPos().down();
            BlockState blockState = world.getBlockState(targetPos);
            world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, targetPos.getY() + 0.7D, targetPos.getZ() + 0.5D, 3, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
            world.playSound(null, targetPos, SoundEvents.BLOCK_GRASS_STEP, SoundCategory.BLOCKS, .8f, .5f);
        }

        if (++counter / level >= SKILL_THRESHOLD) {
            ItemStack stack = entity.getInventory().getStack(indexToPlace);
            Identifier id = Registry.ITEM.getId(stack.getItem());
            Block block = Registry.BLOCK.get(id);
            //settler.getMovementDirection()
            world.setBlockState(blockPos, block.getDefaultState());
            stack.decrement(1);
            entity.getInventory().setStack(indexToPlace, stack);
            indexToPlace = -1;
        }
    }
}
