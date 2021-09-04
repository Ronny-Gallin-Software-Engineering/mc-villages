package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import de.rgse.mc.villages.util.BlockUtil;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.List;
import java.util.Optional;

public class MoveToTreeGoal extends MoveToTargetPosGoal {

    public MoveToTreeGoal(LumberjackEntity mob) {
        super(mob, mob.getSettlerData().getDefaultMovementSpeed(), 100);
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesModuleMemories.TREE);
        return optionalMemory.isPresent() && !isAtTree(optionalMemory.get()) && super.canStart();
    }

    @Override
    public void start() {
        VillagesMod.LOGGER.info("{} move to tree", getMob().getSettlerData().getVillagerName());
        super.start();
    }

    private boolean isAtTree(BlockPos tree) {
        List<BlockPos> surroundingBlocks = BlockUtil.getSurroundingBlocks(getMob().getBlockPos(), 1);
        return surroundingBlocks.stream().anyMatch(bp -> bp.equals(tree));
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesModuleMemories.TREE);

        if (!optionalMemory.isPresent()) {
            return false;
        }

        BlockPos blockPos = optionalMemory.get();
        return blockPos.equals(pos);
    }

    private SettlerEntity getMob() {
        return (SettlerEntity) mob;
    }
}
