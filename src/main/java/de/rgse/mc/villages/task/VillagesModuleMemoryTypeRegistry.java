package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.MemoryModuleTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.BlockPos;

public class VillagesModuleMemoryTypeRegistry {

    public static final MemoryModuleType<BlockPos> CAMPSITE = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("campsite"));
    public static final MemoryModuleType<Boolean> SETTLED = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("settled"));

    public static final MemoryModuleType<BlockPos> KNOW_WOOD = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("know_wood"));
}
