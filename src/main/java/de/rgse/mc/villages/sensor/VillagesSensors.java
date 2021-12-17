package de.rgse.mc.villages.sensor;

import de.rgse.mc.villages.mixin.SensorTypeAccessor;
import de.rgse.mc.villages.poi.VillagesPOI;
import de.rgse.mc.villages.task.VillagesMemories;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.tag.ItemTags;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSensors {

    public static final SensorType<TreeSensor> TREE_SENSOR = SensorTypeAccessor.callRegister(IdentifierUtil.createString("tree_sensor"), TreeSensor::new);
    public static final SensorType<ItemSensor> SAPLING_SENSOR = SensorTypeAccessor.callRegister(IdentifierUtil.createString("sapling_sensor"), () -> new ItemSensor(ItemTags.SAPLINGS, VillagesMemories.SAPLING, 5));
    public static final SensorType<BlockSensor> CHEST_SENSOR = SensorTypeAccessor.callRegister(IdentifierUtil.createString("chest_sensor"), () -> new BlockSensor(Blocks.CHEST, VillagesMemories.CHEST));
    public static final SensorType<POISensor> CRAFTING_TABLE_SENSOR = SensorTypeAccessor.callRegister(IdentifierUtil.createString("poi_senor"), () -> new POISensor(VillagesPOI.CRAFTING_TABLE));

    public static void register() {
    }

}
