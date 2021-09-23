package de.rgse.mc.villages.config;

import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@ConfigEntries
public class WorldConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "world";
    }
}
