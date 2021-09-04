package de.rgse.mc.villages.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;

public class InventoryPanel extends WPlainPanel {

    private final WItemSlot inv;

    /**
     * Constructs a player inventory panel.
     *
     * @param inventory the player inventory
     * @since 2.0.0
     */
    public InventoryPanel(Inventory inventory) {
        int y = 0;

        inv = WItemSlot.of(inventory, 0);
        this.add(inv, 0, y);
    }

    @Override
    public boolean canResize() {
        return false;
    }

    /**
     * Sets the background painter of this inventory widget's slots.
     *
     * @param painter the new painter
     * @return this panel
     */
    @Environment(EnvType.CLIENT)
    @Override
    public WPanel setBackgroundPainter(BackgroundPainter painter) {
        super.setBackgroundPainter(null);
        inv.setBackgroundPainter(painter);
        return this;
    }
}
