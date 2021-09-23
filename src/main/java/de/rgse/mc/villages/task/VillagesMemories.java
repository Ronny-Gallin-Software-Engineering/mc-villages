package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.MemoryModuleTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesMemories {

    public static final MemoryModuleType<Long> EATEN = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("eaten"));

    public static final MemoryModuleType<GlobalPos> TREE = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("tree"));
    public static final MemoryModuleType<GlobalPos> SAPLING = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("sapling"));
    public static final MemoryModuleType<GlobalPos> CHEST = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("chest"));
    public static final MemoryModuleType<Map<PointOfInterestType, List<PointOfInterest>>> POI = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("point_of_interest"));
    public static final MemoryModuleType<Boolean> MISSING_TOOL = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("missing_tool"));
    public static final MemoryModuleType<GlobalPos> VILLAGE = MemoryModuleTypeAccessor.register(IdentifierUtil.createString("village"));
}
