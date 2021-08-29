package de.rgse.mc.villages.item;

import de.rgse.mc.villages.entity.VillagesEntities;
import de.rgse.mc.villages.item.groups.VillagesItemGroups;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesItems {

    public static final Item TUT_ITEM = new TutItem(new FabricItemSettings().group(VillagesItemGroups.TUT_GROUP));
    public static final Item WANDERER_SPAWN_EGG = new SpawnEggItem(VillagesEntities.WANDERER, 12895428, 11382189, new Item.Settings().group(VillagesItemGroups.TUT_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, IdentifierUtil.create("tut_item"), VillagesItems.TUT_ITEM);
        Registry.register(Registry.ITEM, IdentifierUtil.create("wanderer_spawn_egg"), WANDERER_SPAWN_EGG);
    }
}
