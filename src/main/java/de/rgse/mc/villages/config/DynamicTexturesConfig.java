package de.rgse.mc.villages.config;

import lombok.NoArgsConstructor;
import me.lortseam.completeconfig.api.ConfigEntries;
import me.lortseam.completeconfig.api.ConfigGroup;

@NoArgsConstructor
@ConfigEntries
public class DynamicTexturesConfig implements ConfigGroup {

    public static int textureResolution = 1024;
    public static int facesCount = 3;
    public static int hairCountMale = 3;
    public static int hairCountFemale = 3;

    @Override
    public String getId() {
        return "dynamicTexture";
    }
}
