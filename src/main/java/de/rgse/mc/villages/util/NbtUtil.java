package de.rgse.mc.villages.util;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.EntityName;
import de.rgse.mc.villages.entity.Gender;
import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NbtUtil {

    public static void writeSettlerDataToNbt(SettlerEntity settlerEntity, NbtCompound nbt) {

        NbtCompound villageNbt = new NbtCompound();

        SettlerData settlerData = settlerEntity.getSettlerData();

        if (settlerData.getGender() != null) {
            villageNbt.putString(IdentifierUtil.createString("gender"), settlerData.getGender().name());
        }

        if (settlerData.getVillagerName() != null) {
            villageNbt.putString(IdentifierUtil.createString("name"), settlerData.getVillagerName().toString());
        }

        if (settlerData.getProfession() != null) {
            villageNbt.putString(IdentifierUtil.createString("profession"), settlerData.getProfession().getIdentifier().toString());
        }

        villageNbt.put(IdentifierUtil.createString("inventory"), settlerEntity.getInventory().toNbtList());
        nbt.put(VillagesMod.MOD_ID, villageNbt);

    }

    public static void readSettlerDataFromNbt(SettlerEntity settlerEntity, NbtCompound nbt) {
        if (nbt.contains(VillagesMod.MOD_ID)) {
            SettlerData settlerData = settlerEntity.getSettlerData();

            NbtCompound villagesNbt = nbt.getCompound(VillagesMod.MOD_ID);

            if (villagesNbt.contains(IdentifierUtil.createString("gender"))) {
                settlerData.setGender(Gender.valueOf(villagesNbt.getString(IdentifierUtil.createString("gender"))));
            }

            if (villagesNbt.contains(IdentifierUtil.createString("name"))) {
                settlerData.setVillagerName(EntityName.parse(villagesNbt.getString(IdentifierUtil.createString("name"))));
            }

            if (villagesNbt.contains(IdentifierUtil.createString("profession"))) {
                Identifier identifier = Identifier.tryParse(villagesNbt.getString(IdentifierUtil.createString("profession")));
                settlerData.setProfession(VillagesProfessions.of(identifier));
            }

            NbtList inventory = villagesNbt.getList(IdentifierUtil.createString("inventory"), NbtElement.COMPOUND_TYPE);
            settlerEntity.getInventory().readNbtList(inventory);

        }
    }
}
