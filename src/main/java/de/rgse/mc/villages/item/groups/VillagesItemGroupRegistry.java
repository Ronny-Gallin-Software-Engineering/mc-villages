package de.rgse.mc.villages.item.groups;

import de.rgse.mc.villages.item.VillagesItemRegistry;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesItemGroupRegistry {

    public static final ItemGroup VILLAGES_ITEM_GROUP = FabricItemGroupBuilder.build(IdentifierUtil.create("item_group"), () -> new ItemStack(VillagesItemRegistry.COIN));
}
