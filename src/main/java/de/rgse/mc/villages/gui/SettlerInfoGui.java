package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.util.ClockUtil;
import de.rgse.mc.villages.util.IdentifierUtil;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class SettlerInfoGui extends LightweightGuiDescription {

    private final SettlerEntity settler;

    public SettlerInfoGui(@NotNull SettlerEntity settler) {
        this.settler = settler;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        WSprite icon = new WSprite(IdentifierUtil.texture().item().named("coin_item.png"));
        root.add(icon, 0, 2, 1, 1);

        WButton button = new WButton(new TranslatableText("gui.examplemod.examplebutton"));
        root.add(button, 0, 3, 4, 1);

        WLabel labelBorn = new WLabel(new TranslatableText("villages.gui.born"));
        root.add(labelBorn, 0, 4, 2, 1);

        WLabel valueBorn = new WLabel(getBornValue());
        root.add(valueBorn, 4, 4, 1, 1);

        WLabel labelAge = new WLabel(new TranslatableText("villages.gui.age"));
        root.add(labelAge, 0, 5, 2, 1);

        WLabel valueAge = new WLabel(getBirthdayValue());
        root.add(valueAge, 4, 5, 1, 1);

        root.validate(this);
    }

    private Text getBornValue() {
        int age = ClockUtil.getAge(settler);
        return new LiteralText(Integer.toString(age));
    }

    private Text getBirthdayValue() {
        int age = ClockUtil.toDay(settler.getSettlerData().getBirthday());
        return new LiteralText(Integer.toString(age));
    }
}