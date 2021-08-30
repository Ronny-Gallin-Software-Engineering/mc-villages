package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ScheduleAccessor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.ScheduleBuilder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesTaskRegistry {

    public static void register() {
        ScheduleAccessor.setVillagerDefault(
                new ScheduleBuilder(VillagesScheduleRegistry.DEFAULT).withActivity(10, VillagesActivityRegistry.GREET).build());//10 is the start time
    }
}
