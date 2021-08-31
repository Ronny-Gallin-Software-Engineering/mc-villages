package de.rgse.mc.villages.text;

import de.rgse.mc.villages.entity.EntityName;
import de.rgse.mc.villages.entity.settler.SettlerData;
import de.rgse.mc.villages.entity.settler.SettlerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NameText {

    public static Text of(SettlerEntity entity) {
        SettlerData settlerData = entity.getSettlerData();
        EntityName name = settlerData.getVillagerName();

        if(null != name) {
            LiteralText surnameText = new LiteralText(name.getSurname());
            Style style = surnameText.getStyle().withColor(Colors.PRIMARY_TEXT);
            surnameText.setStyle(style);
            return new LiteralText(name.getGivenName()).append(new LiteralText(" ").append(surnameText));

        } else {
            return LiteralText.EMPTY;
        }
    }
}
