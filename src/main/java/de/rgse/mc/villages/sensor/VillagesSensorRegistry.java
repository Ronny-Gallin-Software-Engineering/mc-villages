package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.mixin.SensorTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.tag.BlockTags;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSensorRegistry {

    public static final SensorType<BlockSensor> WOOD_SENSOR = SensorTypeAccessor.register(IdentifierUtil.createString("wood_sendor"), () -> new BlockSensor(BlockTags.LOGS, 32));

    public static void register() {
    }
}
