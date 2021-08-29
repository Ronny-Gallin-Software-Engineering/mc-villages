package de.rgse.mc.villages.util;

import de.rgse.mc.villages.VillagesMod;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.Identifier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentifierUtil {

    public static Identifier create(String value) {
        return new Identifier(VillagesMod.MOD_ID, value);
    }

    public static String createString(String value) {
        return String.join(":", VillagesMod.MOD_ID, value);
    }
}
