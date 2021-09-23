package de.rgse.mc.villages.entity.thought;

import lombok.Data;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

@Data
public class Thought implements Comparable<Thought> {

    private int priority;
    private String name;
    private Item item;

    public Thought(int priority, String name, Item item) {
        this.priority = priority;
        this.name = name;
        this.item = item;
    }


    @Override
    public int compareTo(@NotNull Thought o) {
        return Integer.compare(priority, o.priority);
    }
}
