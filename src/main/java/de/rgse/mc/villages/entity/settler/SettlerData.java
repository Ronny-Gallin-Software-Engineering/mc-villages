package de.rgse.mc.villages.entity.settler;

import de.rgse.mc.villages.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private long birthday;
    private float defaultMovementSpeed;


    public SettlerData() {
        this.gender = Gender.FEMALE;
        this.villagerName = null;
        this.profession = VillagesProfessions.NONE;
        this.mood = new Mood();
        this.defaultMovementSpeed = .4f;
    }

    public static final TrackedDataHandler<SettlerData> SETTLER_DATA = new TrackedDataHandler<>() {

        public void write(PacketByteBuf packetByteBuf, SettlerData settlerData) {
            packetByteBuf.writeString(settlerData.villagerName.toString());
            packetByteBuf.writeString(settlerData.profession.getIdentifier().toString());
            packetByteBuf.writeString(settlerData.gender.toString());
            packetByteBuf.writeLong(settlerData.birthday);
            packetByteBuf.writeFloat(settlerData.defaultMovementSpeed);
        }

        public SettlerData read(PacketByteBuf packetByteBuf) {
            String name = packetByteBuf.readString();
            String professionId = packetByteBuf.readString();
            String genderString = packetByteBuf.readString();

            SettlerData settlerData = new SettlerData();
            settlerData.villagerName = EntityName.parse(name);
            settlerData.gender = Gender.valueOf(genderString);
            settlerData.profession = VillagesProfessions.of(Identifier.tryParse(professionId));
            settlerData.birthday = packetByteBuf.readLong();
            settlerData.defaultMovementSpeed = packetByteBuf.readFloat();

            return settlerData;
        }

        public SettlerData copy(SettlerData settlerData) {
            return settlerData;
        }
    };

    public SettlerData withBirthday(long birthday) {
        return new SettlerData(this.gender, this.villagerName, profession, this.mood, birthday, this.defaultMovementSpeed);
    }

    public SettlerData withProfession(Profession profession) {
        return new SettlerData(this.gender, this.villagerName, profession, this.mood, this.birthday, this.defaultMovementSpeed);
    }

    public SettlerData withName(EntityName name) {
        return new SettlerData(this.gender, name, this.profession, this.mood, this.birthday, this.defaultMovementSpeed);
    }

    public SettlerData withGender(Gender gender) {
        return new SettlerData(gender, this.villagerName, this.profession, this.mood, this.birthday, this.defaultMovementSpeed);
    }

    public SettlerData withDefaultMovementSpeed(float speed) {
        return new SettlerData(gender, this.villagerName, this.profession, this.mood, this.birthday, speed);
    }

    public static void register() {
        TrackedDataHandlerRegistry.register(SETTLER_DATA);
    }
}
