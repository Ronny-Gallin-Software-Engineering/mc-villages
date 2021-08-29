package de.rgse.mc.villages.mixin;

import net.minecraft.entity.ai.brain.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Activity.class)
public interface ActivityAccessor {

    @Invoker("register")
    static Activity register(String id) {
        throw new AssertionError();
    }
}
