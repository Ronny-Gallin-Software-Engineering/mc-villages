package de.rgse.mc.villages.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeAccessor {

    @Invoker("register")
    static PointOfInterestType register(String id, Set<BlockState> workStationStates, int ticketCount, int searchDistance) {
        throw new AssertionError();
    }

    @Invoker("getAllStatesOf")
    static Set<BlockState> getAllStatesOf(Block block) {
        throw new AssertionError();
    }

}
