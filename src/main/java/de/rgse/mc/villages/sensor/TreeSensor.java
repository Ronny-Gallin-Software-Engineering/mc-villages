package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.block.PillarBlockEntity;
import de.rgse.mc.villages.task.VillagesModuleMemoryTypeRegistry;
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
        Optional<BlockPos> optionalMemory = entity.getBrain().getOptionalMemory(VillagesModuleMemoryTypeRegistry.KNOW_WOOD);

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
                PillarBlockEntity blockEntity = (PillarBlockEntity) world.getBlockEntity(blockPos);
                if (blockEntity != null && blockEntity.isNaturallyGenerated()) {
                    BlockPos root = getRoot(blockState, blockPos, world);
                    entity.getBrain().remember(VillagesModuleMemoryTypeRegistry.KNOW_WOOD, root);
                    break;
                }
            }
        }
    }

    private boolean match(BlockState blockState) {
        return BLOCK.contains(blockState.getBlock());
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesModuleMemoryTypeRegistry.KNOW_WOOD);
    }

    private BlockPos getRoot(BlockState blockState, BlockPos blockPos, ServerWorld world) {
        BlockPos beneath = blockPos.down();
        BlockState blockStateBeneath = world.getBlockState(beneath);

        if (blockStateBeneath.isOf(blockState.getBlock())) {
            return getRoot(blockState, beneath, world);

        }

        BlockPos adjacentPos = beneath.west();
        BlockState adjacentBlock = world.getBlockState(beneath.west());
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        adjacentPos = beneath.east();
        adjacentBlock = world.getBlockState(beneath.west());
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        adjacentPos = beneath.north();
        adjacentBlock = world.getBlockState(beneath.west());
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }


        adjacentPos = beneath.south();
        adjacentBlock = world.getBlockState(beneath.west());
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        return blockPos;
    }
}
