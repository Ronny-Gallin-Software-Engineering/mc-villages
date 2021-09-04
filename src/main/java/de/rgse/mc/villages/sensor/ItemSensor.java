package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.task.VillagesModuleMemories;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ItemSensor extends Sensor<SettlerEntity> {

    private final Tag.Identified<Item> requestedItem;
    private MemoryModuleType<BlockPos> memory;
    private final float radius;

    public ItemSensor(Tag.Identified<Item> requestedItem, MemoryModuleType<BlockPos> memory, float radius) {
        this.requestedItem = requestedItem;
        this.memory = memory;
        this.radius = radius;
    }

    @Override
    protected void sense(ServerWorld world, SettlerEntity entity) {
        Optional<BlockPos> optionalMemory = entity.getBrain().getOptionalMemory(memory);

        if (optionalMemory.isEmpty()) {
            List<ItemEntity> saplings = entity.world.getEntitiesByClass(ItemEntity.class, entity.getBoundingBox().expand(radius), this::isItem);

            if (!saplings.isEmpty()) {
                entity.getBrain().remember(memory, saplings.get(0).getBlockPos());
            }
        }
    }


    private boolean isItem(ItemEntity item) {
        return requestedItem.contains(item.getStack().getItem());
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(memory);
    }

}
