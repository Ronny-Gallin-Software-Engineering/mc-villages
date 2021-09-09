package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.stream.Stream;

public class CollectItemsGoal extends MoveToTargetGoal<Item> {

    private static final Logger LOGGER = LogManager.getLogger();

    private int tryCount;

    private CollectItemsGoal(SettlerEntity entity, TargetGoal<Item> delegate) {
        super(entity, delegate, ItemEntity.class);
    }

    @Override
    public boolean canStart() {
        if (super.canStart()) {
            Stream<Item> items = delegate.targetTags != null ? delegate.targetTags.stream().flatMap(t -> t.values().stream()) : delegate.targetBlocks.stream();
            return items.anyMatch(item -> getSettler().getInventory().canInsert(new ItemStack(item, 1)));
        }
        return false;
    }

    @Override
    public void start() {
        LOGGER.info("{} start: {}", getSettler().getSettlerData().getVillagerName(), targetPos);
        VillagesNetwork.notifyStartTask(getSettler(), IdentifierUtil.goal(this));
        tryCount = 0;
        super.start();
    }

    @Override
    public void tick() {
        tryCount++;
        super.tick();
    }

    @Override
    public boolean shouldContinue() {
        boolean giveUp = tryCount >= (20 * 20);

        if (giveUp) {
            LOGGER.info("{} cannot reach target {}. giving up", getSettler().getSettlerData().getVillagerName(), targetPos);
        }

        return Math.sqrt(getSettler().getBlockPos().getSquaredDistance(targetPos)) > 2 && !giveUp;
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", getSettler().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(getSettler(), IdentifierUtil.goal(this));
        super.stop();
    }

    @Override
    protected int getInterval(PathAwareEntity mob) {
        return 20 + mob.getRandom().nextInt(20);
    }

    public static CollectItemsGoal of(SettlerEntity entity, Collection<Item> targetBlocks) {
        return new CollectItemsGoal(entity, TargetGoal.of(targetBlocks));
    }

    public static CollectItemsGoal ofTags(SettlerEntity entity, Collection<Tag.Identified<Item>> targetTags) {
        return new CollectItemsGoal(entity, TargetGoal.ofTags(targetTags));
    }

    @Override
    protected Item toTarget(Entity entity) {
        return ((ItemEntity) entity).getStack().getItem();
    }
}
