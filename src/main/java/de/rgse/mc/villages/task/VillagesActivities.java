package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ActivityAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Activity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesActivities {

    public static final Activity BREAKFAST = ActivityAccessor.callRegister(IdentifierUtil.createString("task_breakfast"));

    public static void register() {
    }

}
