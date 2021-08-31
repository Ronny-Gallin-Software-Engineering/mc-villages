package de.rgse.mc.villages.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class EntityName {
    private String givenName;
    private String surname;

    public String toString() {
        return String.join(" ", givenName, surname);
    }

    public static EntityName parse(String value) {
        if (null == value) {
            return null;
        }

        String[] s = value.split(" ");
        return new EntityName(s[0], s[1]);
    }
}
