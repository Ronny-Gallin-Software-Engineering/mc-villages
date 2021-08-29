package de.rgse.mc.villages.mixin;

import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Schedule.class)
public interface ScheduleAccessor {

    @Invoker("register")
    static ScheduleBuilder register(String id) {
        throw new AssertionError();
    }

    @Accessor("VILLAGER_DEFAULT")
    static void setVillagerDefault(Schedule schedule) {
        throw new UnsupportedOperationException();
    }
}
