package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.wanderer.WandererEntity;
import de.rgse.mc.villages.world.VillagesPOITypeRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static de.rgse.mc.villages.world.VillagesPOITypeRegistry.CAMPING_SITE_BLOCKS;

public class MoveToCampfireGoal extends MoveToTargetPosGoal {

    private static final int THRESHOLD = 2;
    private static final int RANGE = 32;

    public MoveToCampfireGoal(WandererEntity mob) {
        super(mob, .4, RANGE, 10);
    }

    @Override
    public boolean canStart() {
        validateWanderTarget();

        if (getWanderTarget() == null && !getWanderer().isSettled()) {
            List<BlockPos> nearbyCampfires = getNearbyCampfires();

            if (!nearbyCampfires.isEmpty()) {
                BlockPos blockPos = nearbyCampfires.get(0);
                ((WandererEntity) mob).setWanderTarget(blockPos);
            }

        }

        return getWanderTarget() != null && !getWanderer().isSettled() && !isTargetReached() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        boolean continueRun = super.shouldContinue();
        getWanderer().setSettled(continueRun);
        return continueRun;
    }

    private boolean isTargetReached() {
        BlockPos wanderTarget = getWanderTarget();
        return wanderTarget != null && wanderTarget.getSquaredDistance(mob.getBlockPos()) <= 10;
    }

    private void validateWanderTarget() {
        WandererEntity wanderer = getWanderer();

        if (getWanderTarget() != null) {
            Chunk chunk = wanderer.getEntityWorld().getChunk(ChunkSectionPos.getSectionCoord(getWanderTarget().getX()), ChunkSectionPos.getSectionCoord(getWanderTarget().getZ()), ChunkStatus.FULL, false);
            BlockState blockState = chunk.getBlockState(getWanderTarget());

            if (!CAMPING_SITE_BLOCKS.contains(blockState.getBlock())) {
                wanderer.setWanderTarget(null);

            }
        }

        wanderer.setSettled(isTargetReached());
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return pos.equals(calculate(getWanderTarget()));
    }

    private WandererEntity getWanderer() {
        return (WandererEntity) mob;
    }

    private BlockPos getWanderTarget() {
        return getWanderer().getWanderTarget();
    }

    private List<BlockPos> getNearbyCampfires() {
        BlockPos blockPos = new BlockPos(mob.getPos());

        PointOfInterestStorage pointOfInterestStorage = ((ServerWorld) mob.world).getPointOfInterestStorage();
        Stream<PointOfInterest> stream = pointOfInterestStorage.getInCircle(pointOfInterestType -> pointOfInterestType == VillagesPOITypeRegistry.CAMPING_SITE, blockPos, RANGE, PointOfInterestStorage.OccupationStatus.ANY);

        return stream.map(PointOfInterest::getPos).sorted(Comparator.comparingDouble(blockPos2 -> blockPos2.getSquaredDistance(blockPos))).toList();
    }

    private BlockPos calculate(BlockPos blockPos) {
        BlockPos result = blockPos.mutableCopy();

        if (getWanderer().getRandom().nextBoolean()) {
            result = derivate(result, BlockPos::south);

        } else {
            result = derivate(result, BlockPos::north);
        }

        if (getWanderer().getRandom().nextBoolean()) {
            result = derivate(result, BlockPos::west);

        } else {
            result = derivate(result, BlockPos::east);
        }

        return result;
    }

    private BlockPos derivate(BlockPos root, UnaryOperator<BlockPos> supplier) {
        BlockPos blockPos = root;
        Random random = getWanderer().getRandom();

        for (int i = THRESHOLD; i > 0; i--) {
            if (random.nextBoolean()) {
                blockPos = supplier.apply(blockPos);
            }
        }

        return blockPos;
    }
}
