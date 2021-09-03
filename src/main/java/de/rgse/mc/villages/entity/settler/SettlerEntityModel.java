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
        SettlerData settlerData = settler.getSettlerData();
        IdentifierUtil.IdentifierBuilder builder = IdentifierUtil.texture().entity().gender(settlerData.getGender());
        if (settlerData.getMood().isSad()) {
            return builder.formatted("settler/settler_{gender}_sad.png");
        } else {
            return builder.formatted("settler/settler_{gender}.png");
        }
    }

    @Override
    public Identifier getAnimationFileLocation(SettlerEntity animatable) {
        return IdentifierUtil.animation().named("settler.animation.json");
    }

}
