package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesModuleMemoryTypeRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;

import java.util.Random;
import java.util.Set;

public class BlockSensor extends Sensor<SettlerEntity> {

    private final Random random = new Random();
    private final Tag.Identified<Block> block;
    private final int radius;
    private final int sampleCount;

    public BlockSensor(Tag.Identified<Block> block, int radius) {
        this.block = block;
        this.radius = radius;
        sampleCount = (int) Math.pow(radius, 3) / 100;
    }

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        for (int i = 0; i <= sampleCount; i++) {
            BlockPos blockPos = entity.getBlockPos().mutableCopy();

            int x = random.nextInt(radius);
            int y = random.nextInt(radius);
            int z = random.nextInt(radius);

            if (random.nextBoolean()) {
                blockPos.west(x);
            } else {
                blockPos.east(x);
            }

            if (random.nextBoolean()) {
                blockPos.north(z);
            } else {
                blockPos.south(z);
            }

            if (random.nextBoolean()) {
                blockPos.up(y);
            } else {
                blockPos.south(y);
            }

            BlockState blockState = world.getBlockState(blockPos);

            if (block.contains(blockState.getBlock())) {
                entity.getBrain().remember(VillagesModuleMemoryTypeRegistry.KNOW_WOOD, blockPos);
                VillagesMod.LOGGER.info("{} found Log {} at {}", entity.getSettlerData().getVillagerName(), blockState.getBlock().getTranslationKey(), blockPos);
                break;
            }
        }
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(VillagesModuleMemoryTypeRegistry.KNOW_WOOD);
    }
}
