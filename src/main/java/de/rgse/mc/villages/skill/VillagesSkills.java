package de.rgse.mc.villages.skill;

import de.rgse.mc.villages.entity.VillagesProfessions;
import de.rgse.mc.villages.util.IdentifierUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.util.Identifier;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VillagesSkills {

    public static Identifier LUMBERJACK = IdentifierUtil.skill(VillagesProfessions.LUMBERJACK);
}
