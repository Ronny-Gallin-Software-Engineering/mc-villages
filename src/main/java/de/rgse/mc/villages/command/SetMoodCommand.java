package de.rgse.mc.villages.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class SetMoodCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        return 0;
    }

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        LiteralArgumentBuilder<ServerCommandSource> restore = CommandManager.literal("vs:mood")
                .requires(source -> source.hasPermissionLevel(4))
                .then(CommandManager.argument("entity", EntityArgumentType.entities())
                        .then(CommandManager.argument("isSad", BoolArgumentType.bool())
                                .executes(new SetMoodCommand())));

        dispatcher.register(restore);
    }
}
