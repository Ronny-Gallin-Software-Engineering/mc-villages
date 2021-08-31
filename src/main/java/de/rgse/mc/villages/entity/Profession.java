package de.rgse.mc.villages.entity;

import lombok.Data;
import net.minecraft.util.Identifier;

@Data
public class Profession {

    private Identifier identifier;

    public Profession(Identifier identifier){
        this.identifier = identifier;
    }
}
