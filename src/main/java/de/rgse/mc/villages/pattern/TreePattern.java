package de.rgse.mc.villages.pattern;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TreePattern implements Pattern {

    private final int minHeight = 3;

    @Override
    public boolean matches(BlockPos sample, World world) {
        BlockPos root = normaliseSample(sample, world);
        BlockState rootState = world.getBlockState(root);

        if (isLog(rootState)) {
            BlockPos nextLevelLog = root;
            for (int i = 1; i < minHeight; i++) {
                nextLevelLog = getNextLevelLog(nextLevelLog, rootState.getBlock(), world);
                if (nextLevelLog == null) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public static BlockPos normaliseSample(BlockPos sample, World world) {
        BlockState blockState = world.getBlockState(sample);
        return getRoot(blockState, sample, world);
    }

    public static int getTreeSize(BlockPos sample, World world) {
        BlockPos root = normaliseSample(sample, world);
        BlockState rootState = world.getBlockState(root);

        int count = 0;

        if (isLog(rootState)) {
            BlockPos nextLevelLog = root;
            do {
                count++;
                nextLevelLog = getNextLevelLog(nextLevelLog, rootState.getBlock(), world);

            } while (nextLevelLog != null);
        }

        return count;
    }

    public static BlockPos getNextLevelLog(BlockPos blockPos, Block block, World world) {
        BlockPos center = blockPos.up();

        if (world.getBlockState(center).isOf(block)) {
            return center;
        }

        BlockPos north = center.north();
        if (world.getBlockState(north).isOf(block)) {
            return north;
        }

        BlockPos northEast = north.east();
        if (world.getBlockState(northEast).isOf(block)) {
            return northEast;
        }

        BlockPos east = northEast.south();
        if (world.getBlockState(east).isOf(block)) {
            return east;
        }

        BlockPos southEast = east.south();
        if (world.getBlockState(southEast).isOf(block)) {
            return southEast;
        }

        BlockPos south = southEast.west();
        if (world.getBlockState(south).isOf(block)) {
            return south;
        }

        BlockPos southWest = south.west();
        if (world.getBlockState(southWest).isOf(block)) {
            return southWest;
        }

        BlockPos west = southWest.north();
        if (world.getBlockState(west).isOf(block)) {
            return west;
        }

        BlockPos northWest = west.north();
        if (world.getBlockState(northWest).isOf(block)) {
            return northWest;
        }

        return null;
    }

    public static boolean isLog(BlockState blockState) {
        return BlockTags.LOGS.contains(blockState.getBlock());
    }

    private static BlockPos getRoot(BlockState blockState, BlockPos blockPos, World world) {
        BlockPos beneath = blockPos.down();
        BlockState blockStateBeneath = world.getBlockState(beneath);

        if (blockStateBeneath.isOf(blockState.getBlock())) {
            return getRoot(blockState, beneath, world);

        }

        BlockPos adjacentPos = beneath.west();
        BlockState adjacentBlock = world.getBlockState(adjacentPos);
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        adjacentPos = beneath.east();
        adjacentBlock = world.getBlockState(adjacentPos);
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        adjacentPos = beneath.north();
        adjacentBlock = world.getBlockState(adjacentPos);
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }


        adjacentPos = beneath.south();
        adjacentBlock = world.getBlockState(adjacentPos);
        if (adjacentBlock.isOf(blockState.getBlock())) {
            return getRoot(blockState, adjacentPos, world);
        }

        return blockPos;
    }

}
