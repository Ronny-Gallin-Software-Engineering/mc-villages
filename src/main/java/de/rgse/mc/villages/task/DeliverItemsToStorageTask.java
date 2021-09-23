package de.rgse.mc.villages.task;

import de.rgse.mc.villages.config.GameplayConfig;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.util.IdentifierUtil;
import de.rgse.mc.villages.util.ItemStackHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.dynamic.GlobalPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DeliverItemsToStorageTask extends MoveToRememberedPosTask {

    private static final Logger LOGGER = LogManager.getLogger();
    private final Tag.Identified<Item> toDeliver;
    private int inventorySlot = -1;

    public DeliverItemsToStorageTask(SettlerEntity mob, Tag.Identified<Item> toDeliver) {
        super(Map.of(VillagesMemories.CHEST, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), VillagesMemories.CHEST, mob.getSettlerData(), 2, 1000);
        this.toDeliver = toDeliver;
    }

    @Override
    protected boolean shouldRun(ServerWorld serverWorld, SettlerEntity entity) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(VillagesMemories.CHEST);
        GlobalPos chestPos = optionalMemory.get();
        boolean chestStillThere = serverWorld.getRegistryKey() == chestPos.getDimension() && serverWorld.getBlockState(chestPos.getPos()).isOf(Blocks.CHEST);

        if (chestStillThere) {
            inventorySlot = getDeliverableSlot(entity);

            return inventorySlot > -1;
        } else {
            entity.getBrain().forget(VillagesMemories.CHEST);
        }

        return false;
    }

    @Override
    public void run(ServerWorld world, SettlerEntity entity, long time) {
        LOGGER.info("{} start", entity.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(entity, IdentifierUtil.task(this));
        super.run(world, entity, time);
        entity.equipStack(EquipmentSlot.MAINHAND, entity.getInventory().getStack(inventorySlot));
    }

    @Override
    public boolean shouldKeepRunning(ServerWorld world, SettlerEntity entity, long time) {
        return entity.getInventory().getStack(inventorySlot).isIn(toDeliver);
    }

    @Override
    public void keepRunning(ServerWorld world, SettlerEntity entity, long time) {
        Optional<GlobalPos> optionalMemory = entity.getBrain().getOptionalMemory(memory);
        super.keepRunning(world, entity, time);
        if (optionalMemory.isPresent() && hasReached(entity)) {
            ItemStack stackToDeliver = entity.getInventory().getStack(inventorySlot);
            if (stackToDeliver.isIn(toDeliver)) {
                ChestBlockEntity chest = (ChestBlockEntity) entity.world.getBlockEntity(optionalMemory.get().getPos());

                ItemStack remains = stackToDeliver;
                for(int j = 0; j < chest.size(); j++) {
                    ItemStack stack = chest.getStack(j);
                    if (ItemStack.areItemsEqual(stack, remains) && stack.getMaxCount() > stack.getCount()) {
                        List<ItemStack> merge = ItemStackHelper.merge(stack, stackToDeliver);

                        ItemStack stack1 = merge.get(0);
                        chest.setStack(j, stack1);
                        world.playSound(null, chest.getPos(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1f, 1f);

                        if (merge.size() == 2) {
                            remains = merge.get(1);
                            entity.getInventory().setStack(inventorySlot, remains);

                        } else {
                            entity.getInventory().setStack(inventorySlot, ItemStack.EMPTY);
                            break;
                        }
                    } else if (stack.isOf(Items.AIR)) {
                        chest.setStack(j, remains);
                        entity.getInventory().setStack(inventorySlot, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, SettlerEntity entity, long time) {
        LOGGER.info("{} stop", entity.getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(entity, IdentifierUtil.task(this));
        super.finishRunning(world, entity, time);
        this.inventorySlot = -1;
        entity.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }

    private int getDeliverableSlot(SettlerEntity entity) {
        if (inventorySlot == -1) {
            for (int i = 0; i < entity.getInventory().size(); i++) {
                ItemStack stack = entity.getInventory().getStack(i);
                if (stack.isIn(toDeliver) && stack.getCount() >= GameplayConfig.deliverItemThreshold) {
                    return i;
                }
            }
        }

        return inventorySlot;
    }
}
