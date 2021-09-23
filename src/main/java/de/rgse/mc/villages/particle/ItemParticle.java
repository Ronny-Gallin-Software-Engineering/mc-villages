package de.rgse.mc.villages.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ItemParticle extends SpriteBillboardParticle {

    public ItemParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
        super(clientWorld, x, y, z);
        this.maxAge = 75;
        this.x = x;
        this.y = y;
        this.z = z;
        this.velocityX = vx;
        this.velocityY = vy;
        this.velocityZ = vz;
        this.scale = .3f;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.TERRAIN_SHEET;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
            ItemParticle particle = new ItemParticle(clientWorld, x, y, z, vx, vy, vz);

            if (defaultParticleType instanceof ItemParticleEffect effect) {
                particle.setSprite(MinecraftClient.getInstance().getItemRenderer().getModels().getModelParticleSprite(effect.getItem()));
            }

            return particle;
        }
    }
}
