package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SettlerEntityModel extends AnimatedGeoModel<SettlerEntity> {

    @Override
    public Identifier getModelLocation(SettlerEntity object) {
        return IdentifierUtil.geoModel().named("settler.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SettlerEntity settler) {
        return IdentifierUtil.dynamic().id(settler.getId());
    }

    @Override
    public Identifier getAnimationFileLocation(SettlerEntity animatable) {
        return IdentifierUtil.animation().named("settler.animation.json");
    }

}
