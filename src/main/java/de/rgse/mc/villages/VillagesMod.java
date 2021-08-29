package de.rgse.mc.villages;

import de.rgse.mc.villages.entity.VillagesEntities;
import de.rgse.mc.villages.item.VillagesItems;
import de.rgse.mc.villages.task.VillagesActivities;
import de.rgse.mc.villages.task.VillagesSchedules;
import de.rgse.mc.villages.world.VillagesPointOfInterestTypes;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillagesMod implements ModInitializer {

    public static final String MOD_ID = "villages";

    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        VillagesItems.register();
        VillagesEntities.register();
        VillagesPointOfInterestTypes.register();
        VillagesActivities.register();
        VillagesSchedules.register();

        LOGGER.info("{} initialized", MOD_ID);
    }

}
