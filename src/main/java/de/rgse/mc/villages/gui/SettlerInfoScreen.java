package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.text.Text;

public class SettlerInfoScreen extends CottonInventoryScreen<SettlerInfoDescription> {

    public SettlerInfoScreen(SettlerInfoDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        PropertyDelegate propertyDelegate = getScreenHandler().getPropertyDelegate();

        if (propertyDelegate != null) {
            int id = propertyDelegate.get(0);
            Entity entityById = MinecraftClient.getInstance().world.getEntityById(id);

            if (entityById instanceof SettlerEntity settler) {
                getScreenHandler().setSettler(settler);
            }
        }
    }
}
