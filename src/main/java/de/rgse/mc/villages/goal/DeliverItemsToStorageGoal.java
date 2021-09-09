package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.BlockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import de.rgse.mc.villages.util.ItemStackHelper;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DeliverItemsToStorageGoal extends MoveToTargetPosGoal {

    private static final Logger LOGGER = LogManager.getLogger();
    private Tag.Identified<Item> toDeliver;
    private BlockPos chestPos;
    private int inventorySlot = -1;

    public DeliverItemsToStorageGoal(SettlerEntity mob, Tag.Identified<Item> toDeliver) {
        super(mob, mob.getSettlerData().getDefaultMovementSpeed(), 1, 10);
        this.toDeliver = toDeliver;
    }

    @Override
    public boolean canStart() {
        Optional<BlockPos> optionalMemory = getMob().getBrain().getOptionalMemory(VillagesMemories.CHEST);
        boolean knowsChest = optionalMemory.isPresent();

        if (knowsChest) {
            inventorySlot = getDeliverableSlot();

            if (inventorySlot > -1) {
                chestPos = optionalMemory.get();
                boolean chestStillThere = getMob().world.getBlockState(chestPos).isOf(Blocks.CHEST);

                if (chestStillThere) {
                    return super.canStart();
                } else {
                    getMob().getBrain().forget(VillagesMemories.CHEST);
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public void start() {
        LOGGER.info("{} start", getMob().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStartTask(getMob(), IdentifierUtil.goal(this));
        super.start();
    }

    @Override
    public boolean shouldContinue() {
        return !getMob().getInventory().getStack(inventorySlot).isOf(Items.AIR);
    }

    @Override
    public void tick() {
        getMob().lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, Vec3d.of(chestPos));
        super.tick();
        if (hasReached()) {
            ItemStack stackToDeliver = getMob().getInventory().getStack(inventorySlot);
            if (stackToDeliver.isIn(toDeliver)) {
                ChestBlockEntity chest = (ChestBlockEntity) getMob().world.getBlockEntity(chestPos);

                ItemStack remains = stackToDeliver;
                for (int j = 0; j < chest.size(); j++) {
                    ItemStack stack = chest.getStack(j);
                    if (ItemStack.areItemsEqual(stack, remains) && stack.getMaxCount() > stack.getCount()) {
                        List<ItemStack> merge = ItemStackHelper.merge(stack, stackToDeliver);

                        ItemStack stack1 = merge.get(0);
                        chest.setStack(j, stack1);

                        if (merge.size() == 2) {
                            remains = merge.get(1);

                        } else {
                            break;
                        }
                    } else if (stack.isOf(Items.AIR)) {
                        chest.setStack(j, remains);
                        getMob().getInventory().setStack(inventorySlot, ItemStack.EMPTY);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void stop() {
        LOGGER.info("{} stop", getMob().getSettlerData().getVillagerName());
        VillagesNetwork.notifyStopTask(getMob(), IdentifierUtil.goal(this));
        super.stop();
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return pos.getX() == chestPos.getX() && pos.getZ() == chestPos.getZ();
    }

    private SettlerEntity getMob() {
        return (SettlerEntity) mob;
    }

    private int getDeliverableSlot() {
        if (inventorySlot == -1) {
            for (int i = 0; i < getMob().getInventory().size(); i++) {
                if (getMob().getInventory().getStack(i).isIn(toDeliver)) {
                    return i;
                }
            }
        }

        return inventorySlot;
    }
}
