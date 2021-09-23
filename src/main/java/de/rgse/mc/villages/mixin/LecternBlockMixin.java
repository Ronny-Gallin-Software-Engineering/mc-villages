package de.rgse.mc.villages.mixin;

import de.rgse.mc.villages.entity.VillageEntity;
import de.rgse.mc.villages.item.VillagesItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LecternBlock.class)
public class LecternBlockMixin {

    @Inject(
            at = @At("RETURN"),
            cancellable = true,
            remap = false,
            method = "createBlockEntity(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/entity/BlockEntity;"
    )
    public void createBlockEntity(BlockPos pos, BlockState state, CallbackInfoReturnable<BlockEntity> cir) {
        cir.setReturnValue(new VillageEntity(pos, state));
    }

    @Inject(
            at = @At("RETURN"),
            cancellable = true,
            remap = false,
            method = "onUse(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;")
    private void onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        Boolean hasBook = state.get(LecternBlock.HAS_BOOK);
        ActionResult returnValue = cir.getReturnValue();
        if (!hasBook && returnValue.equals(ActionResult.CONSUME)) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!itemStack.isEmpty() && itemStack.isOf(VillagesItems.VILLAGE_FOUNDING_DOCUMENT)) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
