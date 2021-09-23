package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.config.VillageConfig;
import de.rgse.mc.villages.item.VillagesItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LecternBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class VillageEntity extends LecternBlockEntity {

    private Box boundingBox;

    public VillageEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        BlockPos pos1 = pos.add(-1 * VillageConfig.boundingBoxRadius, -1 * VillageConfig.boundingBoxRadius, -1 * VillageConfig.boundingBoxRadius);
        BlockPos pos2 = pos.add(VillageConfig.boundingBoxRadius, VillageConfig.boundingBoxRadius, VillageConfig.boundingBoxRadius);
        boundingBox = new Box(pos1, pos2);
    }

    public boolean isInBoundingBox(BlockPos blockPos) {
        return boundingBox.contains(Vec3d.of(blockPos));
    }

    public boolean isFounded() {
        return this.getBook().isOf(VillagesItems.VILLAGE_FOUNDING_DOCUMENT);
    }

    public boolean hasBook() {
        return super.hasBook() || getBook().isOf(VillagesItems.VILLAGE_FOUNDING_DOCUMENT);
    }
}
