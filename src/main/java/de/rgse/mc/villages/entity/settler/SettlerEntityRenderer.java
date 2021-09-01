package de.rgse.mc.villages.entity.settler;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SettlerEntityRenderer extends GeoEntityRenderer<SettlerEntity> {

    private SettlerEntityRenderer(EntityRendererFactory.Context ctx, SettlerEntityModel targetModel) {
        super(ctx, targetModel);
    }

    public static <E extends SettlerEntityModel> SettlerEntityRenderer of(EntityRendererFactory.Context ctx, Class<E> targetModelClass) {
        try {
            E targetModel = targetModelClass.getConstructor().newInstance();
            return new SettlerEntityRenderer(ctx, targetModel);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
