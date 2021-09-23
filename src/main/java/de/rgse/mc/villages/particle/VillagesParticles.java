package de.rgse.mc.villages.particle;

import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.registry.Registry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesParticles {

    public static final DefaultParticleType SPEECH = FabricParticleTypes.simple();
    public static final DefaultParticleType ITEM = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, IdentifierUtil.create("speech"), SPEECH);
    }

    public static void registerClient() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(IdentifierUtil.particle().named("speech"))));

        ParticleFactoryRegistry.getInstance().register(SPEECH, SpeechParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ITEM, sp -> new ItemParticle.Factory());
    }
}
