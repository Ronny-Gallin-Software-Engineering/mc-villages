package de.rgse.mc.villages.pattern;

import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class BuildingPattern implements Pattern {

    @Override
    public boolean matches(BlockPos sample, World world) {
        return false;
    }

    private BlockPos normalizeSample(BlockPos sample, World world) {
        Optional<BlockPos> door = isDoor(sample.east(), world);
        if (door.isPresent()) {
            return door.get();
        }

        door = isDoor(sample.west(), world);
        if (door.isPresent()) {
            return door.get();
        }

        door = isDoor(sample.north(), world);
        if (door.isPresent()) {
            return door.get();
        }

        door = isDoor(sample.south(), world);
        if (door.isPresent()) {
            return door.get();
        }

        door = isDoor(sample.down(), world);
        return door.orElse(null);
    }

    private Optional<BlockPos> isDoor(BlockPos target, World world) {
        BlockState blockStateTarget = world.getBlockState(target);

        if (blockStateTarget.isIn(BlockTags.WOODEN_DOORS)) {
            BlockState blockStateTargetDown = world.getBlockState(target.down());
            return Optional.of(blockStateTargetDown.isIn(BlockTags.WOODEN_DOORS) ? target.down() : target);
        }

        return Optional.empty();
    }

}
