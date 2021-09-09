package de.rgse.mc.villages.goal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tag.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
class TargetGoal<T> extends Goal {

    protected Collection<Tag.Identified<T>> targetTags;
    protected Collection<T> targetBlocks;

    public static <T> TargetGoal<T> ofTags(Collection<Tag.Identified<T>> targetTags) {
        TargetGoal<T> objectTargetGoal = new TargetGoal<>();
        objectTargetGoal.targetTags = targetTags;
        return objectTargetGoal;
    }

    public static <T> TargetGoal<T> of(Collection<T> targetBlocks) {
        TargetGoal<T> objectTargetGoal = new TargetGoal<>();
        objectTargetGoal.targetBlocks = targetBlocks;
        return objectTargetGoal;
    }

    public boolean canStart() {
        return true;
    }

    protected boolean isTarget(T sample) {
        if (targetBlocks != null) {
            return targetBlocks.stream().anyMatch(tb -> tb.equals(sample));
        } else {
            return targetTags.stream().anyMatch(tt -> tt.contains(sample));
        }
    }
}
