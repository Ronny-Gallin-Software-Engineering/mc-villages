package de.rgse.mc.villages.config;

import lombok.NoArgsConstructor;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@NoArgsConstructor
@ConfigEntries
public class WorldConfig implements ConfigGroup {

    @Override
    public String getId() {
        return "world";
    }
}
