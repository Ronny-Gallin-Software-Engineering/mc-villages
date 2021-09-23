package de.rgse.mc.villages.task;

import de.rgse.mc.villages.entity.ToolUserEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.tag.Tag;

import java.util.Collection;
import java.util.Map;

public class TargetTask<T> extends Task<ToolUserEntity> {

    protected Collection<Tag.Identified<T>> targetTags;
    protected Collection<T> targetBlocks;

    public TargetTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
        this(requiredMemoryState, 60);
    }

    public TargetTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, int runTime) {
        this(requiredMemoryState, runTime, runTime);
    }

    public TargetTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState, int minRunTime, int maxRunTime) {
        super(requiredMemoryState, minRunTime, maxRunTime);
    }

    protected boolean isTarget(T sample) {
        if (targetBlocks != null) {
            return targetBlocks.stream().anyMatch(tb -> tb.equals(sample));
        } else {
            return targetTags.stream().anyMatch(tt -> tt.contains(sample));
        }
    }
}
