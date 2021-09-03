package de.rgse.mc.villages.entity.lumberjack;

import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntityModel;
import de.rgse.mc.villages.util.IdentifierUtil;
import net.minecraft.util.Identifier;

public class LumberjackEntityModel extends SettlerEntityModel {

    @Override
    public Identifier getTextureLocation(SettlerEntity settler) {
        SettlerData settlerData = settler.getSettlerData();
        IdentifierUtil.IdentifierBuilder builder = IdentifierUtil.texture().entity().gender(settlerData.getGender());
        if (settlerData.getMood().isSad()) {
            return builder.formatted("lumberjack/lumberjack_{gender}_sad.png");
        } else {
            return builder.formatted("lumberjack/lumberjack_{gender}.png");
        }
    }
}
