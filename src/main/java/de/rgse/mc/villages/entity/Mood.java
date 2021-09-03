package de.rgse.mc.villages.entity;

import lombok.Data;

import java.util.Random;

@Data
public class Mood {

    private boolean sad = new Random().nextBoolean();

    public float getSpeedModifier() {
        return sad ? .8f : 1f;
    }
}
