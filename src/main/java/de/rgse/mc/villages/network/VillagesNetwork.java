package de.rgse.mc.villages.network;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.network.handler.StartTaskInfoPacketHandler;
import de.rgse.mc.villages.network.handler.StopTaskInfoPacketHandler;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesNetwork {

    private static final Identifier START_TASK_INFO = IdentifierUtil.create("network_start_task");
    private static final Identifier STOP_TASK_INFO = IdentifierUtil.create("network_stop_task");

    public static void notifyStartTask(SettlerEntity settler, Identifier identifierTask) {
        Collection<ServerPlayerEntity> tracking = PlayerLookup.tracking(settler);

        tracking.forEach(player -> {
            PacketByteBuf packetByteBuf = PacketByteBufs.create();
            packetByteBuf.writeString(identifierTask.toString());
            packetByteBuf.writeInt(settler.getId());
            ServerPlayNetworking.send(player, START_TASK_INFO, packetByteBuf);
        });
    }

    public static void notifyStopTask(SettlerEntity settler, Identifier identifierTask) {
        Collection<ServerPlayerEntity> tracking = PlayerLookup.tracking(settler);

        tracking.forEach(player -> {
            PacketByteBuf packetByteBuf = PacketByteBufs.create();
            packetByteBuf.writeString(identifierTask.toString());
            packetByteBuf.writeInt(settler.getId());
            ServerPlayNetworking.send(player, STOP_TASK_INFO, packetByteBuf);
        });
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(START_TASK_INFO, new StartTaskInfoPacketHandler());
        ClientPlayNetworking.registerGlobalReceiver(STOP_TASK_INFO, new StopTaskInfoPacketHandler());
    }

    public static void register() {
    }
}
