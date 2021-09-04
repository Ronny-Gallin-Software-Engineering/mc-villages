package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class CollectSaplingGoal extends Goal {

    private LumberjackEntity entity;
    private List<ItemEntity> items;

    public CollectSaplingGoal(LumberjackEntity entity) {
        this.entity = entity;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    public boolean canStart() {
        Optional<BlockPos> optionalMemory = entity.getBrain().getOptionalMemory(VillagesModuleMemories.SAPLING);

        if (optionalMemory.isPresent()) {
            items = entity.world.getEntitiesByClass(ItemEntity.class, new Box(optionalMemory.get()), this::isSapling);
            boolean empty = items.isEmpty();
            if (empty) {
                entity.getBrain().forget(VillagesModuleMemories.SAPLING);
            }
            return !empty;
        }

        return false;
    }

    public void start() {
        VillagesMod.LOGGER.info("{} collects Saplings", entity.getSettlerData().getVillagerName());
        if (!items.isEmpty()) {
            entity.getNavigation().startMovingTo(items.get(0), entity.getSettlerData().getDefaultMovementSpeed());
        }
    }

    private boolean isSapling(ItemEntity item) {
        return ItemTags.SAPLINGS.contains(item.getStack().getItem());
    }
}
