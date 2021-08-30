package de.rgse.mc.villages.world;

import de.rgse.mc.villages.mixin.PointOfInterestTypeAccessor;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesPOITypeRegistry {

    public static final List<Block> CAMPING_SITE_BLOCKS = Arrays.asList(Blocks.CAMPFIRE);

    public static final PointOfInterestType CAMPING_SITE = PointOfInterestTypeAccessor.register(IdentifierUtil.createString("camping_site"),
            getCampingsite()
            , 32, 100);

    private static Set<BlockState> getCampingsite() {
        return CAMPING_SITE_BLOCKS.stream().flatMap(b -> PointOfInterestTypeAccessor.getAllStatesOf(b).stream()).collect(Collectors.toSet());
    }

    public static void register() {
    }
}
