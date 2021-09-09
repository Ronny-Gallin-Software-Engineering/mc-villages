package de.rgse.mc.villages.skill;

import lombok.Getter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class Skills {

    private final Set<Skill> skills;

    public Skills() {
        skills = new HashSet<>();
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public Optional<Skill> getSkill(Identifier identifier) {
        return skills.stream().filter(s -> s.getIdentifier().equals(identifier)).findFirst();
    }

    public NbtCompound toNbt() {
        NbtList list = new NbtList();
        skills.forEach(s -> list.add(s.toNbt()));

        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("skills", list);
        return nbtCompound;
    }

    public static Skills fromNbt(NbtCompound tag) {
        Skills result = null;
        if (tag != null && tag.contains("skills")) {
            result = new Skills();
            NbtList list = tag.getList("skills", NbtElement.LIST_TYPE);
            for (NbtElement element : list) {
                if (element.getType() == NbtElement.COMPOUND_TYPE) {
                    result.getSkills().add(Skill.fromNbt((NbtCompound) element));
                }
            }
        }
        return result;
    }

    public void setOrReplace(Skill skill) {
        skills.remove(skill);
        skills.add(skill);
    }
}
