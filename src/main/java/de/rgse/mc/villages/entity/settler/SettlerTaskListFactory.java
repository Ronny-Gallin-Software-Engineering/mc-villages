package de.rgse.mc.villages.entity.settler;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.task.settler.FindVillageTask;
import de.rgse.mc.villages.task.settler.OrganizeInventoryTask;
import de.rgse.mc.villages.util.ClockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.task.ScheduleActivityTask;
import net.minecraft.entity.ai.brain.task.Task;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SettlerTaskListFactory {

    public static final Schedule SCHEDULE = ScheduleAccessor.callRegister(IdentifierUtil.createString("schedule_settler")).withActivity(ClockUtil.DAWN, Activity.IDLE).withActivity(ClockUtil.DUSK, Activity.REST).build();

    public static ImmutableList<Pair<Integer, ? extends Task<? super SettlerEntity>>> createIdleTasks() {
        return ImmutableList.of(Pair.of(97, new FindVillageTask()), Pair.of(98, new OrganizeInventoryTask()), Pair.of(99, new ScheduleActivityTask()));
    }
}
