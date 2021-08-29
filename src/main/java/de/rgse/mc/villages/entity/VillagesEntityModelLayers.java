package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class VillagesEntityModelLayers {

    public static final EntityModelLayer WANDERER = new EntityModelLayer(IdentifierUtil.create("wanderer"), "main");
    public static final EntityModelLayer MONK = new EntityModelLayer(IdentifierUtil.create("monk"), "main");
}
