package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.entity.lumberjack.LumberjackEntity;
import de.rgse.mc.villages.entity.lumberjack.LumberjackEntityModel;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntityModel;
import de.rgse.mc.villages.entity.settler.SettlerEntityRenderer;
import de.rgse.mc.villages.entity.wanderer.WandererEntity;
import de.rgse.mc.villages.entity.wanderer.WandererEntityModel;
import de.rgse.mc.villages.entity.wanderer.WandererEntityRenderer;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.model.Dilation;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesEntityRegistry {

    public static final EntityType<WandererEntity> WANDERER = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("wanderer"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WandererEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2f))
                    .build());

    public static final EntityType<SettlerEntity> SETTLER = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("settler"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SettlerEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2f))
                    .build());

    public static final EntityType<LumberjackEntity> LUMBERJACK = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("lumberjack"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LumberjackEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 2f))
                    .build());

    public static void register() {
        FabricDefaultAttributeRegistry.register(VillagesEntityRegistry.WANDERER, WandererEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(VillagesEntityRegistry.SETTLER, SettlerEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(VillagesEntityRegistry.LUMBERJACK, LumberjackEntity.createMobAttributes());
    }

    public static void registerClient() {
        EntityRendererRegistry.register(VillagesEntityRegistry.WANDERER, WandererEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(VillagesEntityModelLayerRegistry.WANDERER, () -> WandererEntityModel.getTexturedModelData(Dilation.NONE));

        EntityRendererRegistry.register(VillagesEntityRegistry.SETTLER, ctx -> SettlerEntityRenderer.of(ctx, SettlerEntityModel.class));
        EntityRendererRegistry.register(VillagesEntityRegistry.LUMBERJACK, ctx -> SettlerEntityRenderer.of(ctx, LumberjackEntityModel.class));
    }
}
