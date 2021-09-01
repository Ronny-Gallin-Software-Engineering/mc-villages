package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntityModel;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LumberjackEntityModel extends SettlerEntityModel {

    @Override
    public Identifier getTextureLocation(SettlerEntity settler) {
        SettlerData settlerData = settler.getSettlerData();
        if (settlerData.getMood().isSad()) {
            return IdentifierUtil.texture().entity().gender(settlerData.getGender()).formatted("lumberjack/lumberjack_{gender}_sad.png");
        } else {
            return IdentifierUtil.texture().entity().gender(settlerData.getGender()).formatted("lumberjack/lumberjack_{gender}.png");
        }
    }
}
