package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class BlockSensor extends Sensor<SettlerEntity> {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Random random = new Random();
    private final Tag.Identified<Block> block;
    private final int radiusVertical;
    private final int radiusHorizontal;
    private final long sampleCount;

    public BlockSensor(Tag.Identified<Block> block, int radiusVertical, int radiusHorizontal) {
        this.block = block;
        this.radiusVertical = radiusVertical;
        this.radiusHorizontal = radiusHorizontal;
        long blockCount = 8L * (radiusHorizontal * radiusHorizontal * radiusVertical);
        sampleCount = blockCount / 20;
    }

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        Optional<BlockPos> optionalMemory = entity.getBrain().getOptionalMemory(VillagesModuleMemories.TREE);

        if (optionalMemory.isPresent()) {
            BlockPos blockPos = optionalMemory.get();
            BlockState rememberedBlock = world.getBlockState(blockPos);
            if (match(rememberedBlock)) {
                return;
            }
        }

        for (int i = 0; i <= sampleCount; i++) {
            BlockPos blockPos = entity.getBlockPos().mutableCopy();

            int x = random.nextInt(radiusHorizontal);
            int y = random.nextInt(radiusVertical);
            int z = random.nextInt(radiusHorizontal);

            if (random.nextBoolean()) {
                blockPos = blockPos.west(x);
            } else {
                blockPos = blockPos.east(x);
            }

            if (random.nextBoolean()) {
                blockPos = blockPos.north(z);
            } else {
                blockPos = blockPos.south(z);
            }

            if (random.nextBoolean()) {
                blockPos = blockPos.up(y);
            } else {
                blockPos = blockPos.south(y);
            }

            BlockState blockState = world.getBlockState(blockPos);
            boolean blockFound = match(blockState);

            if (blockFound) {
                entity.getBrain().remember(VillagesModuleMemories.TREE, blockPos);
                break;
            }
        }
    }

    private boolean match(BlockState blockState) {
        return block.contains(blockState.getBlock());
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesModuleMemories.TREE);
    }

}
