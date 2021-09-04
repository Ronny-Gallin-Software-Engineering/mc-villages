package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.MemoryModuleTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.BlockPos;

public class VillagesModuleMemories {

    public static final MemoryModuleType<BlockPos> CAMPSITE = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("campsite"));
    public static final MemoryModuleType<Boolean> SETTLED = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("settled"));

    public static final MemoryModuleType<BlockPos> TREE = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("tree"));
    public static final MemoryModuleType<BlockPos> SAPLING = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("sapling"));
}
