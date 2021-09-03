package de.rgse.mc.villages.client;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.VillagesEntities;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.gui.VillagesGuis;
import de.rgse.mc.villages.world.VillagesPOITypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VillagesModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VillagesPOITypes.register();

        VillagesEntities.registerClient();
        SettlerData.register();

        VillagesGuis.registerClient();

        VillagesMod.LOGGER.info("{} client initialised", VillagesMod.MOD_ID);
    }
}
