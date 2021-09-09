package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.block.PillarBlockEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.pattern.TreePattern;
import de.rgse.mc.villages.task.VillagesMemories;
import lombok.NoArgsConstructor;
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

@NoArgsConstructor
public class TreeSensor extends Sensor<SettlerEntity> {

    private static final Tag.Identified<Block> BLOCK = BlockTags.LOGS;

    private final Random random = new Random();

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        int radiusHorizontal = (int) entity.getSettlerData().getViewDistance();
        int radiusVertical = radiusHorizontal / 2;
        long blockCount = 8L * (radiusHorizontal * radiusHorizontal * radiusVertical);
        long sampleCount = blockCount / 20;

        Optional<BlockPos> treeMemory = entity.getBrain().getOptionalMemory(VillagesMemories.TREE);

        if (treeMemory.isPresent()) {
            BlockPos rememberedPosition = treeMemory.get();
            BlockState rememberedBlock = world.getBlockState(rememberedPosition);
            if (isRequiredBlockType(rememberedBlock)) {
                return;
            }
        }

        for (int i = 0; i <= sampleCount; i++) {
            BlockPos sample = getSample(entity, radiusHorizontal, radiusVertical);

            BlockState blockState = world.getBlockState(sample);

            if (isRequiredBlockType(blockState)) {
                PillarBlockEntity blockEntity = (PillarBlockEntity) world.getBlockEntity(sample);

                if (blockEntity != null && blockEntity.isNaturallyGenerated()) {
                    entity.getBrain().remember(VillagesMemories.TREE, TreePattern.normaliseSample(sample, world));
                    break;
                }
            }
        }
    }

    private BlockPos getSample(LivingEntity entity, int radiusHorizontal, int radiusVertical) {
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
        return Set.of(VillagesMemories.TREE);
    }

}
