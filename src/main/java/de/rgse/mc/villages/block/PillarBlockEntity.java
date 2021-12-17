package de.rgse.mc.villages.block;

import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

@Getter
@Setter
public class PillarBlockEntity extends BlockEntity {

    private static final String NBT_KEY = IdentifierUtil.createString("naturallyGenerated");

    private boolean naturallyGenerated;

    public PillarBlockEntity(BlockPos pos, BlockState state) {
        super(VillagesBlockEntities.PILLAR_BLOCK_ENTITY, pos, state);
        naturallyGenerated = false;
    }


    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean(NBT_KEY, naturallyGenerated);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        naturallyGenerated = nbt.getBoolean(NBT_KEY);
    }
}
