package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Optional;

public abstract class ToolUserEntity extends SettlerEntity {

    protected ToolUserEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
    }

    public abstract boolean isRequiredTool(Item item);

    public ItemStack getMainTool() {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(this);
        Map<String, Map<String, TrinketInventory>> inventory = trinketComponent.get().getInventory();

        TrinketInventory trinketInventory = inventory.get("hand").get("tool");
        return trinketInventory.getStack(0);
    }

    public void setMainTool(ItemStack stack) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(this);
        Map<String, Map<String, TrinketInventory>> inventory = trinketComponent.get().getInventory();

        TrinketInventory trinketInventory = inventory.get("hand").get("tool");
        trinketInventory.setStack(0, stack);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(this);
        trinketComponent.ifPresent(tc -> {
            NbtCompound villageNbt = nbt.getCompound(VillagesMod.MOD_ID);
            tc.writeToNbt(villageNbt);
            nbt.put(VillagesMod.MOD_ID, villageNbt);
        });
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(VillagesMod.MOD_ID)) {

            Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(this);
            trinketComponent.ifPresent(tc -> {
                NbtCompound villageNbt = nbt.getCompound(VillagesMod.MOD_ID);
                tc.readFromNbt(villageNbt);
            });
        }
    }

    @Override
    protected void mobTick() {
        super.mobTick();
        if(getMainTool().isOf(Items.AIR)) {
            SimpleInventory inventory = getInventory();
            for(int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);
                if(isRequiredTool(stack.getItem())) {
                    setMainTool(stack);
                    inventory.removeStack(i);
                    break;
                }
            }
        }
    }

    public void equipTool() {
        equipStack(EquipmentSlot.MAINHAND, getMainTool());
    }

    public void unequipTool() {
        equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
    }
}
