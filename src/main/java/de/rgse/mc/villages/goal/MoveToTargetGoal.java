package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Optional;

public abstract class MoveToTargetGoal<T> extends MoveToTargetPosGoal {

    protected BlockPos targetPos;
    protected final TargetGoal<T> delegate;
    private final Class<? extends Entity> target;

    protected MoveToTargetGoal(SettlerEntity settler, TargetGoal<T> delegate, Class<? extends Entity> target) {
        super(settler, settler.getSettlerData().getDefaultMovementSpeed(), (int) settler.getSettlerData().getViewDistance(), 5);
        this.delegate = delegate;
        this.target = target;
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> pos = getSettler().world.getEntitiesByClass(target, getSettler().getBoundingBox().expand(getSettler().getSettlerData().getViewDistance()), this::isIt).stream().map(Entity::getBlockPos).findFirst();
        pos.ifPresent(bp -> targetPos = bp);

        return pos.isPresent() && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        BlockPos blockPos = getSettler().getBlockPos();
        return getTargetPos().getX() == blockPos.getX() && getTargetPos().getZ() == getTargetPos().getZ();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return targetPos != null && targetPos.equals(pos);
    }

    @Override
    public void tick() {
        super.tick();
        if (getSettler().getBlockPos().equals(targetPos)) {
            Optional<BlockPos> pos = getSettler().world.getEntitiesByClass(target, getSettler().getBoundingBox().expand(getSettler().getSettlerData().getViewDistance()), this::isIt).stream().map(Entity::getBlockPos).findFirst();
            pos.ifPresent(bp -> targetPos = bp);
        }
    }

    protected abstract T toTarget(Entity entity);

    private boolean isIt(Entity item) {
        return isTarget(toTarget(item));
    }


    protected SettlerEntity getSettler() {
        return (SettlerEntity) mob;
    }

    protected boolean isTarget(T sample) {
        return delegate.isTarget(sample);
    }
}
