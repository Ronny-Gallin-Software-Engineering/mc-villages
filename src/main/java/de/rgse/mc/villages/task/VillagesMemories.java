package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.MemoryModuleTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesMemories {

    public static final MemoryModuleType<Long> EATEN = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("eaten"));

    public static final MemoryModuleType<GlobalPos> TREE = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("tree"));
    public static final MemoryModuleType<GlobalPos> SAPLING = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("sapling"));
    public static final MemoryModuleType<GlobalPos> CHEST = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("chest"));
    public static final MemoryModuleType<Map<PointOfInterestType, List<PointOfInterest>>> POI = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("point_of_interest"));
    public static final MemoryModuleType<Boolean> MISSING_TOOL = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("missing_tool"));
    public static final MemoryModuleType<GlobalPos> VILLAGE = MemoryModuleTypeAccessor.callRegister(IdentifierUtil.createString("village"));
}
