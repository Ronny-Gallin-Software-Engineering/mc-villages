package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.mixin.SensorTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.brain.sensor.SensorType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSensorRegistry {

    public static final SensorType<TreeSensor> TREE_SENSOR = SensorTypeAccessor.register(IdentifierUtil.createString("tree_sensor"), () -> new TreeSensor(5, 32));

    public static void register() {
    }
}
