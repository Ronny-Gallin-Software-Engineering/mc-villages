package de.rgse.mc.villages.entity;

import lombok.Data;

@Data
public class Mood {

    private boolean sad = true;

    public float getSpeedModifier() {
        return sad ? .8f : 1f;
    }
}
