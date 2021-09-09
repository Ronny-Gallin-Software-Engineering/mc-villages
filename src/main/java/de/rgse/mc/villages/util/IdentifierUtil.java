package de.rgse.mc.villages.util;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.Gender;
import de.rgse.mc.villages.entity.Profession;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.Identifier;
import software.bernie.shadowed.fasterxml.jackson.databind.PropertyNamingStrategy;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdentifierUtil {

    public static IdentifierBuilder texture() {
        return new IdentifierBuilder("textures");
    }

    public static IdentifierBuilder animation() {
        return new IdentifierBuilder("animations");
    }

    public static IdentifierBuilder geoModel() {
        return new IdentifierBuilder("geo");
    }

    public static Identifier profession(String value) {
        return new Identifier(VillagesMod.MOD_ID, "profession_" + value);
    }

    public static Identifier create(String value) {
        return new Identifier(VillagesMod.MOD_ID, value);
    }

    public static String createString(String value) {
        return String.join(":", VillagesMod.MOD_ID, value);
    }

    public static Identifier skill(Profession profession) {
        return Identifier.tryParse(profession.getIdentifier().toString() + "_skill");
    }

    public static Identifier goal(Goal goal) {
        return goal(goal.getClass());
    }

    public static Identifier goal(Class<? extends Goal> goal) {
        return IdentifierUtil.create(new PropertyNamingStrategy.SnakeCaseStrategy().translate(goal.getSimpleName()));
    }


    public static class IdentifierBuilder {

        private final StringBuilder identifier;
        private Gender gender;

        private IdentifierBuilder(String type) {
            this.identifier = new StringBuilder(type + "/");
        }

        public IdentifierBuilder item() {
            identifier.append("item/");
            return this;
        }

        public IdentifierBuilder entity() {
            identifier.append("entity/");
            return this;
        }

        public IdentifierBuilder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Identifier named(String name) {
            identifier.append(name);
            return new Identifier(VillagesMod.MOD_ID, identifier.toString());
        }

        public Identifier formatted(String name) {
            String format = gender != null ? getGendered(name) : name;

            identifier.append(format);
            return new Identifier(VillagesMod.MOD_ID, identifier.toString());
        }

        private String getGendered(String name) {
            return name.replace("{gender}", this.gender.name().toLowerCase(Locale.ROOT));
        }
    }
}
