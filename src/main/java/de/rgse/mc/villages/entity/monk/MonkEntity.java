package de.rgse.mc.villages.entity.monk;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.world.World;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class MonkEntity extends VillagerEntity {

    public MonkEntity(EntityType<MonkEntity> entityType, World world) {
        super(entityType, world);
    }
}