package de.rgse.mc.villages.entity.wanderer;

import de.rgse.mc.villages.entity.VillagesEntityModelLayerRegistry;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WandererEntityRenderer extends MobEntityRenderer<WandererEntity, WandererEntityModel> {

    public WandererEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WandererEntityModel(context.getPart(VillagesEntityModelLayerRegistry.WANDERER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WandererEntity entity) {
        return IdentifierUtil.create("textures/entity/wanderer/wanderer.png");
    }
}
