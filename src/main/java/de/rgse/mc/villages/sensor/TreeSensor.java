package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.block.PillarBlockEntity;
import de.rgse.mc.villages.pattern.TreePattern;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class TreeSensor extends Sensor<LivingEntity> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Tag.Identified<Block> BLOCK = BlockTags.LOGS;

    private final Random random = new Random();
    private final int radiusVertical;
    private final int radiusHorizontal;
    private final long sampleCount;

    public TreeSensor(int radiusVertical, int radiusHorizontal) {
        this.radiusVertical = radiusVertical;
        this.radiusHorizontal = radiusHorizontal;
        long blockCount = 8L * (radiusHorizontal * radiusHorizontal * radiusVertical);
        sampleCount = blockCount / 20;
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {
        Optional<BlockPos> treeMemory = entity.getBrain().getOptionalMemory(VillagesModuleMemories.TREE);

        if (treeMemory.isPresent()) {
            BlockPos rememberedPosition = treeMemory.get();
            BlockState rememberedBlock = world.getBlockState(rememberedPosition);
            if (isRequiredBlockType(rememberedBlock)) {
                return;
            }
        }

        for (int i = 0; i <= sampleCount; i++) {
            BlockPos sample = getSample(entity);

            BlockState blockState = world.getBlockState(sample);

            if (isRequiredBlockType(blockState)) {
                TreePattern treePattern = new TreePattern(world);
                PillarBlockEntity blockEntity = (PillarBlockEntity) world.getBlockEntity(sample);

                if (blockEntity != null && blockEntity.isNaturallyGenerated() && treePattern.matches(sample)) {
                    entity.getBrain().remember(VillagesModuleMemories.TREE, TreePattern.normaliseSample(sample, world));
                    break;
                }
            }
        }
    }

    private BlockPos getSample(LivingEntity entity) {
        BlockPos sample = entity.getBlockPos().mutableCopy();

        int x = random.nextInt(radiusHorizontal);
        int y = random.nextInt(radiusVertical);
        int z = random.nextInt(radiusHorizontal);

        if (random.nextBoolean()) {
            sample = sample.west(x);
        } else {
            sample = sample.east(x);
        }

        if (random.nextBoolean()) {
            sample = sample.north(z);
        } else {
            sample = sample.south(z);
        }

        if (random.nextBoolean()) {
            sample = sample.up(y);
        } else {
            sample = sample.south(y);
        }

        return sample;
    }

    private boolean isRequiredBlockType(BlockState blockState) {
        return BLOCK.contains(blockState.getBlock());
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesModuleMemories.TREE);
    }

}
