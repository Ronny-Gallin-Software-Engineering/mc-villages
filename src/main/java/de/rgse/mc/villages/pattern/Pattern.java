package de.rgse.mc.villages.pattern;

import net.minecraft.util.math.BlockPos;

public interface Pattern {

    boolean matches(BlockPos sample);

}
