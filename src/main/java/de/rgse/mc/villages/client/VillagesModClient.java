package de.rgse.mc.villages.client;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.VillagesEntities;
import de.rgse.mc.villages.world.VillagesPointOfInterestTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VillagesModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VillagesPointOfInterestTypes.register();

        VillagesEntities.registerClient();

        VillagesMod.LOGGER.info("{} client initialised", VillagesMod.MOD_ID);
    }
}
