package de.rgse.mc.villages.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.util.TextureUtil;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Slf4j
@Mixin(ResourceTexture.class)
public abstract class ResourceTextureMixin {

    @Accessor
    abstract Identifier getLocation();

    @Invoker
    abstract void callUpload(NativeImage image, boolean blur, boolean clamp);

    @Inject(
            remap = false,
            at = @At(value = "HEAD"),
            cancellable = true,
            method = "load(Lnet/minecraft/resource/ResourceManager;)V")
    public void load(ResourceManager manager, CallbackInfo ci) throws IOException {
        if (getLocation().getNamespace().equals(VillagesMod.MOD_ID) && getLocation().getPath().startsWith("dynamic")) {
            NativeImage nativeImage = TextureUtil.read(getLocation());

            if (!RenderSystem.isOnRenderThreadOrInit()) {
                RenderSystem.recordRenderCall(() -> callUpload(nativeImage, false, false));
            } else {
                callUpload(nativeImage, false, false);
            }

            ci.cancel();
        }

    }
}
