package de.rgse.mc.villages.entity.thought;

import de.rgse.mc.villages.entity.ToolUserEntity;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.util.VillagesParticleUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Thoughts {

    private static final long COOLDOWN = 300;

    private final SettlerEntity settler;
    private final List<Thought> data = new LinkedList<>();
    private long shouldCall;

    public Thoughts(SettlerEntity entity) {
        this.settler = entity;
        shouldCall = COOLDOWN;
    }

    public void tick() {
        if (settler.world.getTime() % 40 == 0) {
            data.clear();
            createThoughts();
            Collections.sort(data);
        }

        if (--shouldCall <= 0 && !settler.world.isClient && !data.isEmpty()) {
            shouldCall = COOLDOWN;
            Thought thought = data.get(0);

            VillagesParticleUtil.spawnItemParticle((ServerWorld) settler.world, settler, thought.getItem());
        }
    }

    private void createThoughts() {
        isHungry();
        hasTool();
    }

    private void isHungry() {
        data.add(new Thought(1, "Hungry", Items.BREAD));
    }

    private void hasTool() {
        if (settler instanceof ToolUserEntity toolUser) {
            ItemStack stack = toolUser.getMainTool();
            if (!toolUser.isRequiredTool(stack.getItem())) {
                data.add(new Thought(5, "Missing Tool", Items.WOODEN_AXE));
            }
        }
    }
}
