package de.rgse.mc.villages.item.groups;

import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesItemGroups {

    public static final ItemGroup TUT_GROUP = FabricItemGroupBuilder.build(IdentifierUtil.create("general"), () -> new ItemStack(Items.COMPOSTER));
}
