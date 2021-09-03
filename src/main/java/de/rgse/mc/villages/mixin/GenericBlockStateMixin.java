package de.rgse.mc.villages.mixin;

import de.rgse.mc.villages.extensions.AbstractBlockStateExtension;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class GenericBlockStateMixin implements AbstractBlockStateExtension {

    private Map<String, Object> villagesData = new HashMap<>();

    public void setProperty(String name, Object value) {
        villagesData.put(name, value);
    }

    public void setProperties(Map<String, Object> source) {
        this.villagesData = source;
    }

    public Optional<Object> getProperty(String name) {
        return Optional.ofNullable(villagesData.get(name));
    }

    @Inject(
            remap = false,
            method = "Lnet/minecraft/block/AbstractBlock$AbstractBlockState;onStateReplaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Z)V",
            at = @At("HEAD")
    )
    public void onStateReplaced(World world, BlockPos pos, BlockState state, boolean moved, CallbackInfo ci) {
        ((AbstractBlockStateExtension) state).setProperties(villagesData);
    }

}
