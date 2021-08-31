package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
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
        if (settlerData.getProfession().equals(VillagesProfessionRegistry.LUMBERJACK)) {
            return IdentifierUtil.texture().entity().gender(settlerData.getGender()).named("settler/lumberjack_{gender}.png");
        } else {
            return IdentifierUtil.texture().entity().named("settler/settler.png");
        }
    }

    @Override
    public Identifier getAnimationFileLocation(SettlerEntity animatable) {
        return IdentifierUtil.animation().named("settler.animation.json");
    }

}
