package de.rgse.mc.villages.command;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesCommandRegistry {

    public static void register() {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> SetMoodCommand.register(dispatcher)));
    }
}
