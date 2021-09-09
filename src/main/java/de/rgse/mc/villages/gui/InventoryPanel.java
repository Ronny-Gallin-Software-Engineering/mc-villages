package de.rgse.mc.villages.gui;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.widget.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class InventoryPanel extends WPlainPanel {

    private final WItemSlot inv;
    @Nullable
    private WWidget label;

    public InventoryPanel(Inventory inventory) {
        this(inventory, null);
    }

    public InventoryPanel(Inventory inventory, @Nullable Text labelText) {
        int y = 0;

        if (labelText != null) {
            this.label = createInventoryLabel(labelText);
            this.add(label, 0, 0, label.getWidth(), label.getHeight());
            y += label.getHeight();
        }

        inv = WItemSlot.of(inventory, 0, inventory.size(), 1);
        this.add(inv, 0, y);
    }

    public WItemSlot getInv() {
        return inv;
    }

    @Override
    public boolean canResize() {
        return false;
    }

    public static WLabel createInventoryLabel(Text labelText) {
        WLabel label = new WLabel(labelText);
        label.setSize(9 * 18, 11);
        return label;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public WPanel setBackgroundPainter(BackgroundPainter painter) {
        super.setBackgroundPainter(null);
        inv.setBackgroundPainter(painter);
        return this;
    }

    @Override
    public void validate(GuiDescription c) {
        super.validate(c);
        inv.validate(c);
        if (c != null && label instanceof WLabel) {
            ((WLabel) label).setColor(c.getTitleColor());
            label.validate(c);
        }
    }
}
