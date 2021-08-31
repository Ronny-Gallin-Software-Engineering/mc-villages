package de.rgse.mc.villages;

import de.rgse.mc.villages.entity.VillagesEntityRegistry;
import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.item.VillagesItemRegistry;
import de.rgse.mc.villages.resource.Resources;
import de.rgse.mc.villages.sensor.VillagesSensorRegistry;
import de.rgse.mc.villages.task.VillagesActivityRegistry;
import de.rgse.mc.villages.task.VillagesScheduleRegistry;
import de.rgse.mc.villages.world.VillagesPOITypeRegistry;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class VillagesMod implements ModInitializer {

    public static final String MOD_ID = "villages";

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        GeckoLib.initialize();

        Resources.init();
        SettlerData.register();

        VillagesProfessionRegistry.register();
        VillagesItemRegistry.register();
        VillagesEntityRegistry.register();
        VillagesPOITypeRegistry.register();
        VillagesActivityRegistry.register();
        VillagesScheduleRegistry.register();
        VillagesSensorRegistry.register();

        LOGGER.info("{} initialized", MOD_ID);
    }

}
