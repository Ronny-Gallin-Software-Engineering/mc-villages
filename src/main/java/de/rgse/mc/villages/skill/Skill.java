package de.rgse.mc.villages.skill;

import de.rgse.mc.villages.entity.Profession;
import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.Getter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Getter
public class Skill {

    private Identifier identifier;
    private Profession profession;
    private float level;

    private Skill() {
    }

    public Skill(Profession profession) {
        this.identifier = IdentifierUtil.skill(profession);
        this.profession = profession;
        this.level = 0f;
    }

    public NbtCompound toNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putString("id", identifier.toString());
        nbtCompound.putString("profession", profession.getIdentifier().toString());
        nbtCompound.putFloat("level", level);
        return nbtCompound;
    }

    public static Skill fromNbt(NbtCompound tag) {
        Skill skill = new Skill();
        skill.identifier = Identifier.tryParse(tag.getString("id"));
        skill.profession = VillagesProfessions.of(Identifier.tryParse(tag.getString("profession")));
        skill.level = tag.getFloat("level");
        return skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return Objects.equals(identifier, skill.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
