package de.rgse.mc.villages.entity.settler;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SettlerEntityRenderer extends GeoEntityRenderer<SettlerEntity> {

    public SettlerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new SettlerEntityModel());
    }

}
