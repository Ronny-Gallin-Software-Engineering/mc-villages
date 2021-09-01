package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.util.ClockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Schedule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesScheduleRegistry {

    public static final Schedule LUMBERJACK = ScheduleAccessor.register(IdentifierUtil.createString("lumberjack_schedule"))
            .withActivity(ClockUtil.seven(ClockUtil.MeridiemIndicator.AM), VillagesActivityRegistry.FIND_WOOD).build();

    public static void register(){}
}
