package de.rgse.mc.villages.mixin;

import de.rgse.mc.villages.block.PillarBlockWithEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Mixin(Blocks.class)
public class BlocksMixin {

    @Inject(
            remap = false,
            method = "Lnet/minecraft/block/Blocks;createLogBlock(Lnet/minecraft/block/MapColor;Lnet/minecraft/block/MapColor;)Lnet/minecraft/block/PillarBlock;",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void createLogBlock(MapColor topMapColor, MapColor sideMapColor, CallbackInfoReturnable<PillarBlock> cir) {
        PillarBlockWithEntity pillarBlockWithEntity = new PillarBlockWithEntity(AbstractBlock.Settings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        cir.setReturnValue(pillarBlockWithEntity);
    }
}
