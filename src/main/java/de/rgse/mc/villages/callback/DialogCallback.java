package de.rgse.mc.villages.callback;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface DialogCallback {

    Event<DialogCallback> EVENT = EventFactory.createArrayBacked(DialogCallback.class,
            listeners -> (player, settler) -> {
                for (DialogCallback listener : listeners) {
                    ActionResult result = listener.interact(player, settler);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player, SettlerEntity settler);
}
