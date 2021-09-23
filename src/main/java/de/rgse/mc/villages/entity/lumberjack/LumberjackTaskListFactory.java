package de.rgse.mc.villages.entity.lumberjack;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.rgse.mc.villages.mixin.ScheduleAccessor;
import de.rgse.mc.villages.task.DeliverItemsToStorageTask;
import de.rgse.mc.villages.task.lumberjack.ChopTreeTask;
import de.rgse.mc.villages.task.lumberjack.CollectSaplingTask;
import de.rgse.mc.villages.task.lumberjack.PlantSaplingTask;
import de.rgse.mc.villages.util.ClockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.tag.ItemTags;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LumberjackTaskListFactory {

    public static final Schedule SCHEDULE = ScheduleAccessor.register(IdentifierUtil.createString("schedule_lumberjack"))
            .withActivity(ClockUtil.DAWN, Activity.WORK)
            .withActivity(ClockUtil.NOON, Activity.IDLE)
            .withActivity(ClockUtil.two(ClockUtil.MeridiemIndicator.PM), Activity.WORK)
            .withActivity(ClockUtil.DUSK, Activity.REST).build();

    public static ImmutableList<Pair<Integer, ? extends Task<? super LumberjackEntity>>> createWorkTasks(LumberjackEntity entity) {
        RandomTask<LumberjackEntity> housekeepingTask = new RandomTask<>(List.of(Pair.of(new CollectSaplingTask(entity.getSettlerData()), 1), Pair.of(new PlantSaplingTask(), 1)));
        return ImmutableList.of(Pair.of(1, new DeliverItemsToStorageTask(entity, ItemTags.LOGS)), Pair.of(2, new ChopTreeTask(entity.getSettlerData())), Pair.of(3, housekeepingTask));
    }
}
