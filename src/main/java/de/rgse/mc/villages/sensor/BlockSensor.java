package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class BlockSensor extends Sensor<SettlerEntity> {

    private final Random random = new Random();
    private final Block targetBlock;
    private MemoryModuleType<GlobalPos> memory;

    public BlockSensor(Block block, MemoryModuleType<GlobalPos> memory) {
        this.targetBlock = block;
        this.memory = memory;
    }

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        float viewDistance = entity.getSettlerData().getViewDistance();
        int radiusHorizontal = (int) viewDistance;
        int radiusVertical = (int) (viewDistance / 2);


        long blockCount = 8L * (radiusHorizontal * radiusHorizontal * radiusVertical);
        long sampleCount = blockCount / 20;
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(memory);

        if (optionalMemory.isPresent() && world.getRegistryKey() == optionalMemory.get().getDimension()) {
            GlobalPos blockPos = optionalMemory.get();
            BlockState rememberedBlock = world.getBlockState(blockPos.getPos());
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
                entity.getBrain().remember(memory, GlobalPos.create(world.getRegistryKey(), blockPos));
                break;
            }
        }
    }

    private boolean match(BlockState blockState) {
        return blockState.isOf(targetBlock);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(memory);
    }

}
