package de.rgse.mc.villages.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CoinItem extends Item {

    public CoinItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = new ItemStack(VillagesItemRegistry.COIN);

        user.playSound(SoundEvents.ENTITY_VILLAGER_TRADE, 1f, 1f);
        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
