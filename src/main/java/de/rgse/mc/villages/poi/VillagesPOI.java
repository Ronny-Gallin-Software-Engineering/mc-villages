package de.rgse.mc.villages.poi;

import de.rgse.mc.villages.mixin.PointOfInterestTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestType;

public class VillagesPOI {

        public static final PointOfInterestType CHEST = PointOfInterestTypeAccessor.callRegister(IdentifierUtil.createString("poi_storage"), PointOfInterestTypeAccessor.callGetAllStatesOf(Blocks.CHEST), Integer.MAX_VALUE, 100);
        public static final PointOfInterestType CRAFTING_TABLE = PointOfInterestTypeAccessor.callRegister(IdentifierUtil.createString("poi_crafting_table"), PointOfInterestTypeAccessor.callGetAllStatesOf(Blocks.CRAFTING_TABLE), Integer.MAX_VALUE, 100);

        public static void register() {}
}
