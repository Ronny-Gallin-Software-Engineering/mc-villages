package de.rgse.mc.villages.entity;

import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.client.render.entity.model.EntityModelLayer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesEntityModelLayers {

    public static final EntityModelLayer WANDERER = new EntityModelLayer(IdentifierUtil.create("wanderer"), "main");
}
