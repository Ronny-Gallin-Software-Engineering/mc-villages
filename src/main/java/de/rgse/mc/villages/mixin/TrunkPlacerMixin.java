package de.rgse.mc.villages.mixin;

import de.rgse.mc.villages.block.PillarBlockEntity;
import de.rgse.mc.villages.block.VillagesBlockEntities;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {

    @Inject(
            remap = false,
            at = @At(value = "RETURN"),
            method = "Lnet/minecraft/world/gen/trunk/TrunkPlacer;getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Ljava/util/function/Function;)Z")
    private static void inject(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, Function<BlockState, BlockState> stateProvider, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            Optional<PillarBlockEntity> blockEntityOptional = world.getBlockEntity(pos, VillagesBlockEntities.PILLAR_BLOCK_ENTITY);
            blockEntityOptional.ifPresent(pillarBlockEntity -> pillarBlockEntity.setNaturallyGenerated(true));
        }
    }
}
