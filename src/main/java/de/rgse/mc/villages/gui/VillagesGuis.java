package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesGuis {

    public static ScreenHandlerType<SettlerInfoDescription> SCREEN_HANDLER_TYPE;

    public static void registerClient() {
        ScreenRegistry.<SettlerInfoDescription, SettlerInfoScreen>register(SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new SettlerInfoScreen(gui, inventory.player, title));
    }

    public static void register() {
        SCREEN_HANDLER_TYPE = ScreenHandlerRegistry.registerSimple(IdentifierUtil.create("test_gui"), (syncId, inventory) -> new SettlerInfoDescription(syncId, inventory, ScreenHandlerContext.EMPTY));
    }

    public static String translationKey(String name) {
        return "villages.gui." + name;
    }
}
