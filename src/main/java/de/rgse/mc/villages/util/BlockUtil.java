package de.rgse.mc.villages.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BlockUtil {

    public static List<BlockPos> getSurroundingBlocks(BlockPos center) {
        return getSurroundingBlocks(center, 1);
    }

    public static List<BlockPos> getSurroundingBlocks(BlockPos center, int threshold) {
        List<BlockPos> around = new LinkedList<>();

        BlockPos pointer = center.north(threshold);
        around.add(pointer);

        pointer = pointer.east(threshold);
        around.add(pointer);

        pointer = pointer.south(threshold);
        around.add(pointer);

        pointer = pointer.south(threshold);
        around.add(pointer);

        pointer = pointer.west(threshold);
        around.add(pointer);

        pointer = pointer.west(threshold);
        around.add(pointer);

        pointer = pointer.north(threshold);
        around.add(pointer);

        pointer.east(threshold);
        around.add(pointer);

        return around;
    }
}
