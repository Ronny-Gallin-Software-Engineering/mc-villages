package de.rgse.mc.villages.client;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.VillagesEntityRegistry;
import de.rgse.mc.villages.world.VillagesPOITypeRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class VillagesModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        VillagesPOITypeRegistry.register();

        VillagesEntityRegistry.registerClient();

        VillagesMod.LOGGER.info("{} client initialised", VillagesMod.MOD_ID);
    }
}
