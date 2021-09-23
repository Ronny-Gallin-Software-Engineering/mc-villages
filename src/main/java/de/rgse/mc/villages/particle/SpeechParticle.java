package de.rgse.mc.villages.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SpeechParticle extends SpriteBillboardParticle {

    protected SpeechParticle(ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
        super(clientWorld, x, y, z);
        velocityX = vx;
        velocityY = vy;
        velocityZ = vz;
        scale *= 3;
        maxAge = 75;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double vx, double vy, double vz) {
            SpeechParticle particle = new SpeechParticle(clientWorld, x, y, z, vx, vy, vz);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
