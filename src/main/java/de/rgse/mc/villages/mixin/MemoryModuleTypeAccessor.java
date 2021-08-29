package de.rgse.mc.villages.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MemoryModuleType.class)
public interface MemoryModuleTypeAccessor {

    @Invoker("register")
    static <U> MemoryModuleType<U> register(String id) {
        throw new AssertionError();
    }

    @Invoker("register")
    static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        throw new AssertionError();
    }
}
