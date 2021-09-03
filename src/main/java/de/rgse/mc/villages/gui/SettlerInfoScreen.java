package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SettlerInfoScreen extends CottonClientScreen {

    public SettlerInfoScreen(SettlerEntity settler) {
        super(settler.getDisplayName(), new SettlerInfoGui(settler));
    }
}