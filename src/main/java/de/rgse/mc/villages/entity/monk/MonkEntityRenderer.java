package de.rgse.mc.villages.entity.monk;

import de.rgse.mc.villages.entity.VillagesEntityModelLayers;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class MonkEntityRenderer extends MobEntityRenderer<MonkEntity, MonkEntityModel> {

    public MonkEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MonkEntityModel(context.getPart(VillagesEntityModelLayers.MONK)), 0.5f);
    }

    @Override
    public Identifier getTexture(MonkEntity entity) {
        return IdentifierUtil.create("textures/entity/monk/monk.png");
    }
}
