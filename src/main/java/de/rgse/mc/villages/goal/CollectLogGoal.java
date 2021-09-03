package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Optional;

public class CollectLogGoal extends MoveToTargetPosGoal {

    public CollectLogGoal(LumberjackEntity mob) {
        super(mob, mob.getMovementSpeed(), 32);
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesModuleMemories.TREE);
        return optionalMemory.isPresent();
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
