package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SettlerInfoDescription extends SyncedGuiDescription {

    private static final int INVENTORY_SIZE = 6;

    private SettlerEntity settlerEntity;
    private final List<Identifier> goals = new LinkedList<>();

    public SettlerInfoDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(VillagesGuis.SCREEN_HANDLER_TYPE, syncId, playerInventory, getInventory(context), getPropertyDelegate(context));

        int gridSize = 18;
        WGridPanel root = new WGridPanel(gridSize);
        setRootPanel(root);
        root.setSize(300, 200);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory, 0);
        itemSlot.setInsertingAllowed(false);
        itemSlot.setModifiable(false);
        int index = 2;
        root.add(itemSlot, 0, index);

        for (int i = 1; i < blockInventory.size(); i++) {
            itemSlot = WItemSlot.of(blockInventory, i);
            itemSlot.setInsertingAllowed(false);
            itemSlot.setModifiable(false);
            root.add(itemSlot, i + 1, index);
        }
        index++;

        settlerEntity = getSettler(context);

        WListPanel<Identifier, WLabel> goalsPanel = new WListPanel<>(goals, () -> new WLabel(""), (i, l) -> {
            l.setText(new TranslatableText(i.getPath()));
        });
        goalsPanel.setSize(300, 50);

        root.add(goalsPanel, 0, index++, gridSize, 3);

        root.validate(this);
    }

    private static Inventory getInventory(ScreenHandlerContext ctx) {
        SimpleInventory fallback = new SimpleInventory(INVENTORY_SIZE);
        return ctx.get((world, pos) -> {

            SettlerEntity settler = getSettler(ctx);
            if (settler != null) {
                SimpleInventory simpleInventory = new SimpleInventory(INVENTORY_SIZE);

                if (settler instanceof ToolUserEntity toolUser) {
                    simpleInventory.setStack(0, toolUser.getMainTool());
                }

                for (int i = 0; i < settler.getInventory().size(); i++) {
                    simpleInventory.setStack(i + 1, settler.getInventory().getStack(i));
                }

                return simpleInventory;
            }

            return fallback;

        }).orElse(fallback);
    }

    public SettlerEntity getSettlerEntity() {
        return settlerEntity;
    }

    private static SettlerEntity getSettler(ScreenHandlerContext ctx) {
        return ctx.get((world, pos) -> {
            List<SettlerEntity> entitiesByClass = world.getEntitiesByClass(SettlerEntity.class, new Box(pos), Objects::nonNull);

            SettlerEntity result = null;
            if (!entitiesByClass.isEmpty()) {
                result = entitiesByClass.get(0);
            }

            return result;
        }).orElse(null);
    }

    private static PropertyDelegate getPropertyDelegate(ScreenHandlerContext ctx) {
        SettlerEntity settler = getSettler(ctx);
        if (settler == null) {
            return new ArrayPropertyDelegate(1);
        } else {
            return settler.getPropertyDelegate();
        }
    }

    public void setSettler(SettlerEntity settler) {
        this.settlerEntity = settler;
        this.goals.clear();
        this.goals.addAll(settler.getRunningGoals());

        getRootPanel().validate(this);
    }
}
