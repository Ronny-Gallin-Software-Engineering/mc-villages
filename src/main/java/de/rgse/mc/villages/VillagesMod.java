package de.rgse.mc.villages;

import de.rgse.mc.villages.block.VillagesBlockEntities;
import de.rgse.mc.villages.command.VillagesCommandRegistry;
import de.rgse.mc.villages.config.GameplayConfig;
import de.rgse.mc.villages.config.WorldConfig;
import de.rgse.mc.villages.entity.VillagesEntityRegistry;
import de.rgse.mc.villages.entity.VillagesProfessionRegistry;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.gui.VillagesGuiRegistry;
import de.rgse.mc.villages.item.VillagesItemRegistry;
import de.rgse.mc.villages.resource.VillagesResources;
import de.rgse.mc.villages.sensor.VillagesSensorRegistry;
import de.rgse.mc.villages.task.VillagesActivityRegistry;
import de.rgse.mc.villages.task.VillagesScheduleRegistry;
import de.rgse.mc.villages.world.VillagesPOITypeRegistry;
import me.lortseam.completeconfig.data.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class VillagesMod implements ModInitializer {

    public static final String MOD_ID = "villages";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final Config CONFIG = new Config(MOD_ID, new WorldConfig(), new GameplayConfig());

    @Override
    public void onInitialize() {
        CONFIG.load();

        GeckoLib.initialize();

        VillagesResources.init();
        SettlerData.register();

        VillagesGuiRegistry.register();
        VillagesCommandRegistry.register();
        VillagesProfessionRegistry.register();
        VillagesItemRegistry.register();
        VillagesEntityRegistry.register();
        VillagesPOITypeRegistry.register();
        VillagesActivityRegistry.register();
        VillagesScheduleRegistry.register();
        VillagesSensorRegistry.register();

        VillagesBlockEntities.register();

        LOGGER.info("{} initialized", MOD_ID);
    }

}
