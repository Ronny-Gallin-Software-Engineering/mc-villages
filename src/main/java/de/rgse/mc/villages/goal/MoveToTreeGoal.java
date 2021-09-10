package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class MoveToTreeGoal extends MoveToTargetPosGoal {

    private static final Logger LOGGER = LogManager.getLogger();

    public MoveToTreeGoal(LumberjackEntity mob) {
        super(mob, mob.getSettlerData().getDefaultMovementSpeed(), 2);
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesMemories.TREE);
        return optionalMemory.isPresent() && super.canStart();
    }

    @Override
    public void start() {
        LOGGER.info("{} start", getMob().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(getMob(), IdentifierUtil.goal(this));
        super.start();
    }

    @Override
    public void tick() {
        super.tick();
        if (targetPos.getSquaredDistance(getMob().getBlockPos()) <= 25) {
            Direction movementDirection = getMob().getMovementDirection();
            BlockPos blockInFront = getMob().getBlockPos().add(movementDirection.getVector());
            if (getMob().world.getBlockState(blockInFront).isIn(BlockTags.LEAVES)) {
                getMob().world.breakBlock(blockInFront, true, getMob());
            }
            blockInFront = blockInFront.up();
            if (getMob().world.getBlockState(blockInFront).isIn(BlockTags.LEAVES)) {
                getMob().world.breakBlock(blockInFront, true, getMob());
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !hasReached() && super.shouldContinue();
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 5d;
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", getMob().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(getMob(), IdentifierUtil.goal(this));
        super.stop();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesMemories.TREE);

        if (!optionalMemory.isPresent()) {
            return false;
        }

        BlockPos blockPos = optionalMemory.get();
        return blockPos.getX() == pos.getX() && blockPos.getZ() == pos.getZ();
    }

    private SettlerEntity getMob() {
        return (SettlerEntity) mob;
    }
}
