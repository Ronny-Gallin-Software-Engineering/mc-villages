package de.rgse.mc.villages.gui;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class SettlerInfoScreen extends CottonInventoryScreen<SettlerInfoDescription> {

    public SettlerInfoScreen(SettlerInfoDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
