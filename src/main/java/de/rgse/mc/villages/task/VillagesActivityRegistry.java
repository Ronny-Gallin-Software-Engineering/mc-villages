package de.rgse.mc.villages.task;

import de.rgse.mc.villages.mixin.ActivityAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.Activity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesActivityRegistry {

    public static final Activity GREET = ActivityAccessor.register(IdentifierUtil.createString("greet"));

    public static void register() {
    }

}
