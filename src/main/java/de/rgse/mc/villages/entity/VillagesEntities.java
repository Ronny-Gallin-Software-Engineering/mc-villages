package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.lumberjack.LumberjackEntityModel;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntityModel;
import de.rgse.mc.villages.entity.settler.SettlerEntityRenderer;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesEntities {

    public static final EntityType<SettlerEntity> SETTLER = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("settler"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SettlerEntity::new)
                    .dimensions(EntityDimensions.fixed(.9f, 1.9f))
                    .build());

    public static final EntityType<LumberjackEntity> LUMBERJACK = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("lumberjack"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LumberjackEntity::new)
                    .dimensions(EntityDimensions.fixed(.9f, 1.9f))
                    .build());

    public static void register() {
        FabricDefaultAttributeRegistry.register(VillagesEntities.SETTLER, SettlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(VillagesEntities.LUMBERJACK, LumberjackEntity.createMobAttributes());
    }

    public static void registerClient() {
        EntityRendererRegistry.register(VillagesEntities.SETTLER, ctx -> SettlerEntityRenderer.of(ctx, SettlerEntityModel.class));
        EntityRendererRegistry.register(VillagesEntities.LUMBERJACK, ctx -> SettlerEntityRenderer.of(ctx, LumberjackEntityModel.class));
    }
}
