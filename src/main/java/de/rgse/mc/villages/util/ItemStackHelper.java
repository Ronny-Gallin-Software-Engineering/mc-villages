package de.rgse.mc.villages.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.item.ItemStack;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemStackHelper {

    public static List<ItemStack> merge(ItemStack stackA, ItemStack stackB) {
        if (stackA.isEmpty()) {
            return List.of(stackB);
        }

        if (stackB.isEmpty()) {
            return List.of(stackA);
        }

        ItemStack copyA = stackA.copy();
        ItemStack copyB = stackB.copy();

        if (copyA.isOf(copyB.getItem())) {

            if (copyA.getMaxCount() == copyA.getCount()) {
                return List.of(copyA, copyB);
            }

            if (copyB.getMaxCount() == copyB.getCount()) {
                return List.of(copyB, copyA);
            }

            int capacity = copyA.getMaxCount() - copyA.getCount();
            int mergeValue = Math.min(capacity, copyB.getCount());

            copyA.increment(mergeValue);
            copyB.decrement(mergeValue);

            return copyB.isEmpty() ? List.of(copyA) : List.of(copyA, copyB);

        } else {
            return List.of(stackA, stackB);
        }
    }
}
