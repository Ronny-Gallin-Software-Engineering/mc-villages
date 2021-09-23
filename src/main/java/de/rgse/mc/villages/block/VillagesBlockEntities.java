package de.rgse.mc.villages.block;

import de.rgse.mc.villages.entity.VillageEntity;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesBlockEntities {

    public static BlockEntityType<PillarBlockEntity> PILLAR_BLOCK_ENTITY;
    public static BlockEntityType<VillageEntity> VILLAGE_BLOCK_ENTITY;

    public static void register() {
        List<Block> blocks = Registry.BLOCK.stream().filter(block -> PillarBlock.class.isAssignableFrom(block.getClass()) && block.getDefaultState().getMaterial() == Material.WOOD).toList();
        Block[] b = blocks.toArray(new Block[0]);

        PILLAR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, IdentifierUtil.create("pillar_block_entity"), FabricBlockEntityTypeBuilder.create(PillarBlockEntity::new, b).build());
        VILLAGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, IdentifierUtil.create("village_block_entity"), FabricBlockEntityTypeBuilder.create(VillageEntity::new, Blocks.LECTERN).build());
    }
}
