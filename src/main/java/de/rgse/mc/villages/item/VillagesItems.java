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

    public static final Item COIN = new CoinItem(new FabricItemSettings().group(VillagesItemGroups.VILLAGES_ITEM_GROUP));
    public static final Item COIN_STACK = new CoinStackItem(new FabricItemSettings().group(VillagesItemGroups.VILLAGES_ITEM_GROUP));

    public static final Item WANDERER_SPAWN_EGG = new SpawnEggItem(VillagesEntities.WANDERER, 12895428, 11382189, new Item.Settings().group(VillagesItemGroups.VILLAGES_ITEM_GROUP));
    public static final Item LUMBERJACK_SPAWN_EGG = new SpawnEggItem(VillagesEntities.LUMBERJACK, 123456, 654321, new Item.Settings().group(VillagesItemGroups.VILLAGES_ITEM_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, IdentifierUtil.create("coin_item"), VillagesItems.COIN);
        Registry.register(Registry.ITEM, IdentifierUtil.create("coin_stack_item"), VillagesItems.COIN_STACK);
        Registry.register(Registry.ITEM, IdentifierUtil.create("wanderer_spawn_egg"), WANDERER_SPAWN_EGG);
        Registry.register(Registry.ITEM, IdentifierUtil.create("lumberjack_spawn_egg"), LUMBERJACK_SPAWN_EGG);
    }
}
