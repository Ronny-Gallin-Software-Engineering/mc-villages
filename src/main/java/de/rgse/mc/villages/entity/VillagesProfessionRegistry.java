package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.mixin.RegistryAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesProfessionRegistry {

    public static final Profession NONE = new Profession(IdentifierUtil.profession("none"));
    public static final Profession LUMBERJACK = new Profession(IdentifierUtil.profession("lumberjack"));

    private static Registry<Profession> registry;

    public static void register() {
        registry = RegistryAccessor.create(RegistryKey.ofRegistry(IdentifierUtil.create("professions")), () -> NONE);
        Registry.register(registry, NONE.getIdentifier(), NONE);
        Registry.register(registry, LUMBERJACK.getIdentifier(), LUMBERJACK);
    }

    public static Profession of(Identifier identifier) {
        return registry.get(identifier);
    }
}
