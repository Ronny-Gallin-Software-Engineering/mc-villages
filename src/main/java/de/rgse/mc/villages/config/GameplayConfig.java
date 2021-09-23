package de.rgse.mc.villages.config;

import lombok.NoArgsConstructor;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@NoArgsConstructor
@ConfigEntries
public class GameplayConfig implements ConfigGroup {

    public static boolean settlerDoAge = false;
    public static int deliverItemThreshold = 16;

    @Override
    public String getId() {
        return "gameplay";
    }
}
