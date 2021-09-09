package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.World;

public abstract class ToolUserEntity extends SettlerEntity {

    protected SimpleInventory mainTool;

    protected ToolUserEntity(EntityType<? extends SettlerEntity> entityType, World world) {
        super(entityType, world);
        mainTool = new SimpleInventory(1);
    }

    public SimpleInventory getMainTool() {
        return mainTool;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtCompound villageNbt = nbt.getCompound(VillagesMod.MOD_ID);
        villageNbt.put("mainTool", mainTool.toNbtList());
        nbt.put(VillagesMod.MOD_ID, villageNbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(VillagesMod.MOD_ID)) {
            NbtCompound villagesNbt = nbt.getCompound(VillagesMod.MOD_ID);
            NbtList inventory = villagesNbt.getList(IdentifierUtil.createString("mainTool"), NbtElement.COMPOUND_TYPE);
            this.mainTool.readNbtList(inventory);
        }
    }

}
