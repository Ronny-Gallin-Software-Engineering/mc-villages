package de.rgse.mc.villages;

import de.rgse.mc.villages.block.VillagesBlockEntities;
import de.rgse.mc.villages.command.VillagesCommands;
import de.rgse.mc.villages.config.DynamicTexturesConfig;
import de.rgse.mc.villages.config.GameplayConfig;
import de.rgse.mc.villages.config.VillageConfig;
import de.rgse.mc.villages.config.WorldConfig;
import de.rgse.mc.villages.entity.VillagesEntities;
import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.gui.VillagesGuis;
import de.rgse.mc.villages.item.VillagesItems;
import de.rgse.mc.villages.network.VillagesNetwork;
import de.rgse.mc.villages.particle.VillagesParticles;
import de.rgse.mc.villages.poi.VillagesPOI;
import de.rgse.mc.villages.resource.VillagesResources;
import de.rgse.mc.villages.sensor.VillagesSensors;
import de.rgse.mc.villages.task.VillagesActivities;
import de.rgse.mc.villages.task.VillagesSchedules;
import de.rgse.mc.villages.world.VillagesPOITypes;
import me.lortseam.completeconfig.data.Config;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

public class VillagesMod implements ModInitializer {

    public static final String MOD_ID = "villages";

    public static final Logger LOGGER = LogManager.getLogger();

    public static final Config CONFIG = new Config(MOD_ID, new WorldConfig(), new GameplayConfig(), new VillageConfig(), new DynamicTexturesConfig());

    @Override
    public void onInitialize() {
        CONFIG.load();

        GeckoLib.initialize();

        VillagesResources.init();
        SettlerData.register();

        VillagesGuis.register();
        VillagesCommands.register();
        VillagesProfessions.register();
        VillagesItems.register();
        VillagesEntities.register();
        VillagesPOITypes.register();
        VillagesActivities.register();
        VillagesSchedules.register();
        VillagesSensors.register();
        VillagesNetwork.register();
        VillagesParticles.register();
        VillagesPOI.register();

        VillagesBlockEntities.register();

        LOGGER.info("{} initialized", MOD_ID);
    }

}
