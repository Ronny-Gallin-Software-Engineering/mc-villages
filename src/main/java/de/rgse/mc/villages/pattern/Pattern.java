package de.rgse.mc.villages.pattern;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface Pattern {

    boolean matches(BlockPos sample, World world);

}
