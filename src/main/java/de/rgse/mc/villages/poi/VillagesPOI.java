package de.rgse.mc.villages.poi;

import de.rgse.mc.villages.mixin.PointOfInterestTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagesPOI {

        public static final PointOfInterestType CHEST = PointOfInterestTypeAccessor.register(IdentifierUtil.createString("poi_storage"), PointOfInterestTypeAccessor.getAllStatesOf(Blocks.CHEST), Integer.MAX_VALUE, 100);
        public static final PointOfInterestType CRAFTING_TABLE = PointOfInterestTypeAccessor.register(IdentifierUtil.createString("poi_crafting_table"), PointOfInterestTypeAccessor.getAllStatesOf(Blocks.CRAFTING_TABLE), Integer.MAX_VALUE, 100);

        public static void register() {}
}
