package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Schedule;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSchedules {

    public static final Schedule DEFAULT = ScheduleAccessor.register(IdentifierUtil.createString("default_schedule")).withActivity(10, VillagesActivities.GREET).build();

    public static void register(){}
}
