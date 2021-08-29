package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.entity.monk.MonkEntity;
import de.rgse.mc.villages.entity.monk.MonkEntityModel;
import de.rgse.mc.villages.entity.monk.MonkEntityRenderer;
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
public class VillagesEntities {

    public static final EntityType<WandererEntity> WANDERER = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("wanderer"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WandererEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build());

    public static final EntityType<MonkEntity> MONK = Registry.register(Registry.ENTITY_TYPE, IdentifierUtil.create("monk"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MonkEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .build());

    public static void register() {
        FabricDefaultAttributeRegistry.register(VillagesEntities.WANDERER, WandererEntity.createMobAttributes());
        FabricDefaultAttributeRegistry.register(VillagesEntities.MONK, MonkEntity.createMobAttributes());
    }

    public static void registerClient() {
        EntityRendererRegistry.register(VillagesEntities.WANDERER, WandererEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(VillagesEntityModelLayers.WANDERER, () -> WandererEntityModel.getTexturedModelData(Dilation.NONE));

        EntityRendererRegistry.register(VillagesEntities.MONK, MonkEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(VillagesEntityModelLayers.MONK, () -> MonkEntityModel.getTexturedModelData(Dilation.NONE));
    }
}
