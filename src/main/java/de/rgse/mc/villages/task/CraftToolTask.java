package de.rgse.mc.villages.task;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.skill.Skill;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.Optional;

public class CraftToolTask extends MoveToRememberedPosTask<ToolUserEntity> {

    private static final int SKILL_THRESHOLD = 500;
    private final Item defaultTool;
    private final Identifier skillIdentifier;
    private int counter;

    public CraftToolTask(SettlerData settlerData, Item defaultTool, Identifier skillIdentifier) {
        super(Map.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT, VillagesMemories.MISSING_TOOL, MemoryModuleState.VALUE_PRESENT), VillagesMemories.CHEST, settlerData, 2, 1000);
        this.defaultTool = defaultTool;
        this.skillIdentifier = skillIdentifier;
    }

    @Override
    protected void keepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        super.keepRunning(world, entity, time);

        if (hasReached(entity)) {
            Optional<Skill> skill = entity.getSettlerData().getSkills().getSkill(skillIdentifier);
            float level = skill.map(Skill::getLevel).orElse(1f);

            if (counter % 20 == 0) {
                BlockPos targetPos = entity.getBlockPos().down();
                BlockState blockState = world.getBlockState(targetPos);
                world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), targetPos.getX() + 0.5D, targetPos.getY() + 0.7D, targetPos.getZ() + 0.5D, 3, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, (world.getRandom().nextFloat() - 0.5D) * 0.08D, 0.15000000596046448D);
                world.playSound(null, targetPos, SoundEvents.BLOCK_GRASS_STEP, SoundCategory.BLOCKS, .8f, .5f);
            }

            if (++counter / level >= SKILL_THRESHOLD) {
                ItemStack stack = new ItemStack(defaultTool, 1);
                entity.setMainTool(stack);
            }
        }
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, ToolUserEntity entity, long time) {
        Optional<Boolean> optional = entity.getBrain().getOptionalMemory(VillagesMemories.MISSING_TOOL);
        return optional.isPresent() &&  super.shouldKeepRunning(world, entity, time);
    }
}
