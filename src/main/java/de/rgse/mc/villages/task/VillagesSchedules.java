package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.util.ClockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Schedule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSchedules {

    public static final Schedule DEFAULT_SCHEDULE = ScheduleAccessor.register(IdentifierUtil.createString("settler_default_schedule"))
            .withActivity(ClockUtil.seven(ClockUtil.MeridiemIndicator.AM), VillagesActivities.BREAKFAST).build();

    public static void register(){}
}
