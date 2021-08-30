package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.mixin.SensorTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.sensor.SensorType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSensorRegistry {

    public static final SensorType<CampsiteSensor> CAMPSITE_SENSOR = SensorTypeAccessor.register(IdentifierUtil.createString("campsite_sensor"), CampsiteSensor::new);

    public static void register() {
    }
}
