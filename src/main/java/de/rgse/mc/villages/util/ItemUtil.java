package de.rgse.mc.villages.util;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.text.Colors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemUtil {

    private static final String PRODUCED_BY_SETTLER = IdentifierUtil.createString("producedBySettler");

    public static void markAsVillagesStack(ItemStack stack, SettlerEntity entity) {
        NbtCompound nbtCompound = stack.getNbt() != null ? stack.getNbt() : new NbtCompound();
        nbtCompound.putInt(PRODUCED_BY_SETTLER, entity.getId());
        stack.setNbt(nbtCompound);

        Text name = stack.getName();
        stack.setCustomName(name.copy().setStyle(name.getStyle().withColor(Colors.PRIMARY_TEXT)));

        MutableText append = new TranslatableText("item.tooltip.produced_By").append(entity.getCustomName());
        stack.getItem().appendTooltip(stack, entity.world, List.of(stack.getName(), append), null);
    }

    public static void markAsVillagesStack(Collection<ItemStack> stack, SettlerEntity entity) {
        stack.forEach(s -> ItemUtil.markAsVillagesStack(s, entity));
    }

    public static boolean isMarkedAsVillagesStack(ItemStack itemStack) {
        NbtCompound nbt = itemStack.getNbt();
        return nbt != null && nbt.contains(PRODUCED_BY_SETTLER);
    }
}
