package de.rgse.mc.villages.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries
public class VillageConfig implements ConfigGroup {

    public static int boundingBoxRadius = 256;

    public String getId() {
        return "village";
    }
}
