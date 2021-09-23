package de.rgse.mc.villages.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.CompositeTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.util.collection.WeightedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CompositeTask.class)
public interface CompositeTaskAccessor<E extends LivingEntity> {

    @Accessor
    CompositeTask.Order getOrder();

    @Accessor
    CompositeTask.RunMode getRunMode();

    @Accessor
    WeightedList<Task<? extends E>> getTasks();
}
