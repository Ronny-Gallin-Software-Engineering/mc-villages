package de.rgse.mc.villages.gui;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesGuis {

    public static void registerClient() {
    }

    public static void register() {
    }

    public static String translationKey(String name) {
        return "villages.gui." + name;
    }
}
