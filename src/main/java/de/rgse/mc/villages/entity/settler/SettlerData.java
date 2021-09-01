package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

@AllArgsConstructor
@Data
public class SettlerData {

    private Gender gender;

    private EntityName villagerName;

    private Profession profession;

    private Mood mood;

    public SettlerData() {
        this.gender = Gender.FEMALE;
        this.villagerName = null;
        this.profession = VillagesProfessionRegistry.NONE;
        this.mood = new Mood();
    }

    public static final TrackedDataHandler<SettlerData> SETTLER_DATA = new TrackedDataHandler<>() {

        public void write(PacketByteBuf packetByteBuf, SettlerData settlerData) {
            packetByteBuf.writeString(settlerData.villagerName.toString());
            packetByteBuf.writeString(settlerData.profession.getIdentifier().toString());
            packetByteBuf.writeString(settlerData.gender.toString());
        }

        public SettlerData read(PacketByteBuf packetByteBuf) {
            String name = packetByteBuf.readString();
            String professionId = packetByteBuf.readString();
            String genderString = packetByteBuf.readString();

            SettlerData settlerData = new SettlerData();
            settlerData.villagerName = EntityName.parse(name);
            settlerData.gender = Gender.valueOf(genderString);
            settlerData.profession = VillagesProfessionRegistry.of(Identifier.tryParse(professionId));

            return settlerData;
        }

        public SettlerData copy(SettlerData settlerData) {
            return settlerData;
        }
    };

    public SettlerData withProfession(Profession profession) {
        return new SettlerData(this.gender, this.villagerName, profession, this.mood);
    }

    public SettlerData withName(EntityName name) {
        return new SettlerData(this.gender, name, this.profession, this.mood);
    }

    public SettlerData withGender(Gender gender) {
        return new SettlerData(gender, this.villagerName, this.profession, this.mood);
    }

    public static void register() {
        TrackedDataHandlerRegistry.register(SETTLER_DATA);
    }
}
