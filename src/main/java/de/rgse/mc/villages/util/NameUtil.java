package de.rgse.mc.villages.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.entity.EntityName;
import de.rgse.mc.villages.entity.Gender;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NameUtil {

    private static final Type LIST_TYPE = new TypeToken<List<GivenName>>() {
    }.getType();
    private static final Type STRING_LIST_TYPE = new TypeToken<List<String>>() {
    }.getType();

    private static NameUtil instance;

    private final List<String> givenMale = new LinkedList<>();
    private final List<String> givenFemale = new LinkedList<>();
    private final List<String> surnames = new LinkedList<>();

    private final Random random = new Random();

    public static NameUtil instance() {
        if (null == instance) {
            instance = new NameUtil();
        }

        return instance;
    }

    public static void initialize(Collection<Identifier> names, ResourceManager manager) {
        Gson gson = new Gson();

        for (Identifier id : names) {
            try (InputStream stream = manager.getResource(id).getInputStream(); InputStreamReader inputStreamReader = new InputStreamReader(stream)) {
                if (id.getPath().contains("female")) {
                    List<GivenName> gn = gson.fromJson(inputStreamReader, LIST_TYPE);
                    instance().givenFemale.clear();
                    instance().givenFemale.addAll(formatNames(gn));

                } else if (id.getPath().contains("male")) {
                    List<GivenName> gn = gson.fromJson(inputStreamReader, LIST_TYPE);
                    instance().givenMale.clear();
                    instance().givenMale.addAll(formatNames(gn));

                } else if (id.getPath().contains("surnames")) {
                    instance().surnames.clear();
                    instance().surnames.addAll(gson.fromJson(inputStreamReader, STRING_LIST_TYPE));
                }

            } catch (Exception e) {
                VillagesMod.LOGGER.error("Error occurred while loading resource json " + id.toString(), e);
            }

        }
    }

    public EntityName createName(Gender gender) {
        return new EntityName(gender == Gender.FEMALE ? getGivenNameFemale() : getGivenNameMale(), getSurname());
    }

    public EntityName createName(Gender gender, SettlerEntity parent) {
        SettlerData settlerData = parent.getSettlerData();
        return new EntityName(gender == Gender.FEMALE ? getGivenNameFemale() : getGivenNameMale(), settlerData.getVillagerName().getSurname());

    }

    public String getSurname() {
        int i = random.nextInt(surnames.size());
        return surnames.get(i - 1);
    }

    public String getGivenNameFemale() {
        int i = random.nextInt(givenFemale.size());
        return givenFemale.get(i - 1);
    }

    public String getGivenNameMale() {
        int i = random.nextInt(givenMale.size());
        return givenMale.get(i - 1);
    }

    private static List<String> formatNames(Collection<GivenName> gn) {
        return gn.stream().map(GivenName::getName).map(n -> n.charAt(0) + n.substring(1).toLowerCase(Locale.ROOT)).toList();
    }

    @Data
    private static class GivenName {
        private String name;
    }
}
