package de.rgse.mc.villages.util;

import de.rgse.mc.villages.config.DynamicTexturesConfig;
import de.rgse.mc.villages.entity.Gender;
import de.rgse.mc.villages.entity.Profession;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Environment(EnvType.CLIENT)
public class TextureUtil {

    public static NativeImage read(Identifier identifier) throws IOException {
        String[] split = identifier.getPath().split("id_");
        int i = Integer.parseInt(split[1]);
        SettlerEntity entity = (SettlerEntity) MinecraftClient.getInstance().world.getEntityById(i);
        Profession profession = entity.getSettlerData().getProfession();

        List<Identifier> textureFractions = new LinkedList<>();

        Identifier body = IdentifierUtil.texture().entity().body().profession(profession).gender(entity.getSettlerData().getGender()).formatted("{profession}_{gender}.png");

        List<Identifier> head = head(entity);

        textureFractions.add(body);
        textureFractions.addAll(head);

        return combine(textureFractions);
    }

    private static NativeImage combine(List<Identifier> images) throws IOException {
        BufferedImage result = new BufferedImage(
                DynamicTexturesConfig.textureResolution, DynamicTexturesConfig.textureResolution,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();

        for (Identifier image : images) {
            Resource resource = MinecraftClient.getInstance().getResourceManager().getResource(image);
            BufferedImage bi = ImageIO.read(resource.getInputStream());
            g.drawImage(bi, 0, 0, null);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        ImageIO.write(result, "png", byteArrayOutputStream);

        return NativeImage.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
    }

    private static List<Identifier> head(SettlerEntity entity) {
        int entityHash = Integer.hashCode(entity.getId());

        Identifier face = IdentifierUtil.texture().entity().head().face((entityHash % DynamicTexturesConfig.facesCount) + 1).named(".png");
        Identifier hair;

        if(entity.getSettlerData().getGender() == Gender.FEMALE) {
            hair = IdentifierUtil.texture().entity().head().gender(entity.getSettlerData().getGender()).hair((entityHash % DynamicTexturesConfig.hairCountFemale) + 1).formatted("_{gender}.png");

        } else {
            hair = IdentifierUtil.texture().entity().head().gender(entity.getSettlerData().getGender()).hair((entityHash % DynamicTexturesConfig.hairCountMale) + 1).formatted("_{gender}.png");
        }

        return Arrays.asList(face, hair);
    }
}
