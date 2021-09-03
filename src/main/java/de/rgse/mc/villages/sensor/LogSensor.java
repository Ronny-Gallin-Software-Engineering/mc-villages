package de.rgse.mc.villages.sensor;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

public class LogSensor extends BlockSensor {

    public LogSensor(Tag.Identified<Block> block, int radiusVertical, int radiusHorizontal) {
        super(block, radiusVertical, radiusHorizontal);
    }

}
