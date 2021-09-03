package de.rgse.mc.villages.item;

import de.rgse.mc.villages.entity.VillagesEntityRegistry;
import de.rgse.mc.villages.item.groups.VillagesItemGroupRegistry;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesItemRegistry {

    public static final Item COIN = new CoinItem(new FabricItemSettings().group(VillagesItemGroupRegistry.VILLAGES_ITEM_GROUP));
    public static final Item COIN_STACK = new CoinStackItem(new FabricItemSettings().group(VillagesItemGroupRegistry.VILLAGES_ITEM_GROUP));

    public static final Item WANDERER_SPAWN_EGG = new SpawnEggItem(VillagesEntityRegistry.WANDERER, 12895428, 11382189, new Item.Settings().group(VillagesItemGroupRegistry.VILLAGES_ITEM_GROUP));
    public static final Item LUMBERJACK_SPAWN_EGG = new SpawnEggItem(VillagesEntityRegistry.LUMBERJACK, 123456, 654321, new Item.Settings().group(VillagesItemGroupRegistry.VILLAGES_ITEM_GROUP));

    public static void register() {
        Registry.register(Registry.ITEM, IdentifierUtil.create("coin_item"), VillagesItemRegistry.COIN);
        Registry.register(Registry.ITEM, IdentifierUtil.create("coin_stack_item"), VillagesItemRegistry.COIN_STACK);
        Registry.register(Registry.ITEM, IdentifierUtil.create("wanderer_spawn_egg"), WANDERER_SPAWN_EGG);
        Registry.register(Registry.ITEM, IdentifierUtil.create("lumberjack_spawn_egg"), LUMBERJACK_SPAWN_EGG);
    }
}
