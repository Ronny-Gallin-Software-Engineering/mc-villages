package de.rgse.mc.villages.task.settler;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.util.ItemStackHelper;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Map;

public class OrganizeInventoryTask extends Task<SettlerEntity> {

    private static final long COOLDOWN = 500;
    private long tickCount = 0;

    public OrganizeInventoryTask() {
        super(Map.of());
    }

    @Override
    protected boolean shouldRun(ServerWorld world, SettlerEntity entity) {
        return tickCount++ >= COOLDOWN;
    }

    @Override
    protected void run(ServerWorld world, SettlerEntity entity, long time) {
        tickCount = 0;
        SimpleInventory inventory = entity.getInventory();
        mergeStacks(inventory);
        moveStacksToFront(inventory);
    }

    private void mergeStacks(SimpleInventory inventory) {
        for (int i = inventory.size() - 1; i > 0; i--) {
            ItemStack stack = inventory.getStack(i);

            if (!stack.isEmpty()) {
                for (int j = i - 1; j >= 0; j--) {
                    ItemStack stack1 = inventory.getStack(j);

                    if (ItemStack.areItemsEqual(stack, stack1)) {
                        List<ItemStack> merge = ItemStackHelper.merge(stack, stack1);
                        inventory.setStack(j, merge.get(0));

                        if (merge.size() > 1) {
                            inventory.setStack(i, merge.get(1));
                        } else {
                            inventory.setStack(i, ItemStack.EMPTY);
                        }

                        break;

                    } else if (stack1.isEmpty()) {
                        inventory.setStack(j, stack);
                        inventory.setStack(i, ItemStack.EMPTY);

                        break;
                    }
                }
            }
        }
    }

    private void moveStacksToFront(SimpleInventory inventory) {
        for (int i = 1; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            for (int j = 0; j < i; j++) {
                ItemStack stack1 = inventory.getStack(j);
                if(stack1.isEmpty()) {
                    inventory.setStack(j, stack);
                    inventory.setStack(i, ItemStack.EMPTY);
                    break;
                }
            }
        }
    }
}
