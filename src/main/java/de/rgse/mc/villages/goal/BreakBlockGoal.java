package de.rgse.mc.villages.goal;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import net.minecraft.entity.ai.goal.Goal;

public class BreakBlockGoal extends Goal {

    private SettlerEntity settler;

    public BreakBlockGoal(SettlerEntity settler) {
        this.settler = settler;
    }

    protected SettlerEntity getSettler() {
        return settler;
    }
}
