package de.rgse.mc.villages.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.minecraft.util.Identifier;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Profession {

    private Identifier identifier;
    private String translationKey;
}
