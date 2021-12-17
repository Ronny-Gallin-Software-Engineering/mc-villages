package de.rgse.mc.villages.structure;

import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Optional;

@Getter
public class House {

    private Box entrance;
    private BlockPos marker;
    private World world;

    public House(BlockPos marker, World world) {
        this.marker = marker;
        this.world = world;
    }

    public void initialize(Direction direction) {
        entrance = getEntrance(direction);
    }

    private Box getEntrance(Direction direction) {
        Optional<Box> entrance;

        if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
            entrance = isEntrance(marker.north(), world, direction);
            if (entrance.isPresent()) {
                return entrance.get();
            }

            entrance = isEntrance(marker.south(), world, direction);
            if (entrance.isPresent()) {
                return entrance.get();
            }

        } else {
            entrance = isEntrance(marker.east(), world, direction);
            if (entrance.isPresent()) {
                return entrance.get();
            }

            entrance = isEntrance(marker.west(), world, direction);
            if (entrance.isPresent()) {
                return entrance.get();
            }
        }

        entrance = isEntrance(marker.down(), world, direction);
        return entrance.orElse(null);
    }

    private Optional<Box> isEntrance(BlockPos target, World world, Direction direction) {
        BlockState blockStateTarget = world.getBlockState(target);

        if (isEntrance(blockStateTarget)) {
            BlockState blockStateTargetDown = world.getBlockState(target.down());

            BlockPos lowerPartOfEntrance = isEntrance(blockStateTargetDown) ? target.down() : target;
            BlockPos neighbor = lowerPartOfEntrance.add(direction.getVector());

            if (!isEntrance(world.getBlockState(neighbor)) || isEntrance(world.getBlockState(neighbor.add(direction.getVector())))) {
                return Optional.of(new Box(lowerPartOfEntrance, lowerPartOfEntrance.up()));
            } else {
                return Optional.of(new Box(lowerPartOfEntrance, neighbor.up()));
            }
        }

        return Optional.empty();
    }

    private boolean isEntrance(BlockState blockState) {
        return blockState.isIn(BlockTags.WOODEN_DOORS) || blockState.isIn(BlockTags.FENCE_GATES) || blockState.isOf(Blocks.AIR);
    }
}
