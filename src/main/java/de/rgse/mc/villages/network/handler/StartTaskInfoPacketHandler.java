package de.rgse.mc.villages.network.handler;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class StartTaskInfoPacketHandler implements ClientPlayNetworking.PlayChannelHandler {

    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        String identifierTask = buf.readString();
        int entityId = buf.readInt();

        client.execute(() -> {
            Entity entityById = client.world.getEntityById(entityId);
            if (entityById instanceof SettlerEntity settler) {
                settler.getRunningGoals().add(Identifier.tryParse(identifierTask));
            }
        });
    }
}
