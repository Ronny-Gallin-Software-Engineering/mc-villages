package de.rgse.mc.villages.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.util.Identifier;

@AllArgsConstructor
@Data
public class Profession {

    private Identifier identifier;
    private String translationKey;
}
