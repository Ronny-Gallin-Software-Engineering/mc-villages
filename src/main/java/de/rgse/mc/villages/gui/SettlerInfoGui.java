package de.rgse.mc.villages.gui;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import de.rgse.mc.villages.util.ClockUtil;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WListPanel;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class SettlerInfoGui extends LightweightGuiDescription {

    private final SettlerEntity settler;

    private int index = 2;

    public SettlerInfoGui(@NotNull SettlerEntity settler) {
        this.settler = settler;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        WLabel labelBorn = new WLabel(new TranslatableText(VillagesGuis.translationKey("born")).append(":"));
        root.add(labelBorn, 0, index);
        WLabel valueBorn = new WLabel(new TranslatableText(VillagesGuis.translationKey("day")).append(" ").append(getBornValue()));
        root.add(valueBorn, 2, index);

        WLabel labelAge = new WLabel(new TranslatableText(VillagesGuis.translationKey("age")).append(":"));
        root.add(labelAge, 5, index);
        WLabel valueAge = new WLabel(getBirthdayValue().append(" ").append(new TranslatableText(VillagesGuis.translationKey("days"))));
        root.add(valueAge, 7, index);

        WLabel goalLabel = new WLabel(new TranslatableText(VillagesGuis.translationKey("goals")));
        root.add(goalLabel, 0, ++index);

        List<PrioritizedGoal> runningGoals = settler.getRunningGoals();

        if (!runningGoals.isEmpty()) {
            WListPanel<PrioritizedGoal, WLabel> goalPanel = new WListPanel<>(runningGoals, () -> new WLabel(""), (goal, label) -> {
                label.setText(new TranslatableText(goal.getGoal().getClass().getSimpleName()));
            });
            root.add(goalPanel, 0, index);

        } else {
            WLabel wLabel = new WLabel(new TranslatableText(VillagesGuis.translationKey("idle")));
            root.add(wLabel, 0, index);
        }

        root.validate(this);
    }

    private MutableText getBornValue() {
        int age = ClockUtil.getAge(settler);
        return new LiteralText(Integer.toString(age));
    }

    private MutableText getBirthdayValue() {
        int age = ClockUtil.toDay(settler.getSettlerData().getBirthday());
        return new LiteralText(Integer.toString(age));
    }
}