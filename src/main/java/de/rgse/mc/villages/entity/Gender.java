package de.rgse.mc.villages.entity;

import net.minecraft.world.World;

public enum Gender {

    MALE, FEMALE;

    public static Gender random(World world) {
        return world.getRandom().nextBoolean() ? FEMALE : MALE;
    }
}
