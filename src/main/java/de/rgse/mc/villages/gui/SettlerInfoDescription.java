package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.Box;

import java.util.List;
import java.util.Objects;

public class SettlerInfoDescription extends SyncedGuiDescription {

    private static final int INVENTORY_SIZE = 6;

    public SettlerInfoDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(VillagesGuis.SCREEN_HANDLER_TYPE, syncId, playerInventory, getInventory(context), getPropertyDelegate(context));

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        itemSlot.setInsertingAllowed(false);
        itemSlot.setModifiable(false);
        root.add(itemSlot, 0, 2);

        for (int i = 1; i < blockInventory.size(); i++) {
            itemSlot = WItemSlot.of(blockInventory, i);
            itemSlot.setInsertingAllowed(false);
            itemSlot.setModifiable(false);
            root.add(itemSlot, i + 1, 2);
        }

        root.validate(this);
    }

    private static Inventory getInventory(ScreenHandlerContext ctx) {
        SimpleInventory fallback = new SimpleInventory(INVENTORY_SIZE);
        return ctx.get((world, pos) -> {
            List<SettlerEntity> entitiesByClass = world.getEntitiesByClass(SettlerEntity.class, new Box(pos), Objects::nonNull);

            if (!entitiesByClass.isEmpty()) {
                SettlerEntity settler = entitiesByClass.get(0);
                SimpleInventory simpleInventory = new SimpleInventory(INVENTORY_SIZE);

                if (settler instanceof ToolUserEntity) {
                    simpleInventory.setStack(0, ((ToolUserEntity) settler).getMainTool().getStack(0));
                }

                for (int i = 0; i < settler.getInventory().size(); i++) {
                    simpleInventory.setStack(i + 1, settler.getInventory().getStack(i));
                }

                return simpleInventory;
            }

            return fallback;
        }).orElse(fallback);
    }

    private static PropertyDelegate getPropertyDelegate(ScreenHandlerContext ctx) {
        return new ArrayPropertyDelegate(0);
    }
}
