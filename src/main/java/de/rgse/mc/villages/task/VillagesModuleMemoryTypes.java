package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.MemoryModuleTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.entity.ai.brain.MemoryModuleType;

public class VillagesModuleMemoryTypes {

    public static final MemoryModuleType<Boolean> SAY_HELLO = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("hello"));
}
