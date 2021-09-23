package de.rgse.mc.villages.pattern;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HousePattern implements Pattern {

    @Override
    public boolean matches(BlockPos sample, World world) {
        BlockPos blockPos = normalizeSample(sample);
        return false;
    }

    private BlockPos normalizeSample(BlockPos sample) {
        return null;
    }
}
