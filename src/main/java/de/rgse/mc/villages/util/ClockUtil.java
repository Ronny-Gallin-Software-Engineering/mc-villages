package de.rgse.mc.villages.util;

import de.rgse.mc.villages.entity.settler.SettlerEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClockUtil {

    private static final int NORM = 1000;

    public static final int DAWN = 0;
    public static final int NOON = 6000;
    public static final int DUSK = 13500;
    public static final int MIDNIGHT = 18000;

    public static final BigDecimal TICKS_PER_DAY = BigDecimal.valueOf(20 * 60 * 100);

    public static int toDay(long ticks) {
        return BigDecimal.valueOf(ticks).divide(TICKS_PER_DAY, RoundingMode.DOWN).intValue();
    }

    public static int getAge(SettlerEntity settler) {
        long time = settler.world.getTime();
        return BigDecimal.valueOf(time - settler.getSettlerData().getBirthday()).divide(TICKS_PER_DAY, RoundingMode.DOWN).intValue();
    }

    public static int one(MeridiemIndicator afternoon) {
        return 7 * NORM + six(afternoon);
    }

    public static int two(MeridiemIndicator afternoon) {
        return one(afternoon) + 1000;
    }

    public static int three(MeridiemIndicator afternoon) {
        return two(afternoon) + 1000;
    }

    public static int four(MeridiemIndicator afternoon) {
        return three(afternoon) + 1000;
    }

    public static int five(MeridiemIndicator afternoon) {
        return four(afternoon) + 1000;
    }

    public static int six(MeridiemIndicator afternoon) {
        return afternoon == MeridiemIndicator.PM ? 12000 : 0;
    }

    public static int seven(MeridiemIndicator afternoon) {
        return six(afternoon) + 1000;
    }

    public static int eight(MeridiemIndicator afternoon) {
        return seven(afternoon) + 1000;
    }

    public static int nine(MeridiemIndicator afternoon) {
        return eight(afternoon) + 1000;
    }

    public static int ten(MeridiemIndicator afternoon) {
        return nine(afternoon) + 1000;
    }

    public static int eleven(MeridiemIndicator afternoon) {
        return ten(afternoon) + 1000;
    }

    public static int twelve(MeridiemIndicator afternoon) {
        return eleven(afternoon) + 1000;
    }

    public enum MeridiemIndicator {
        AM, PM
    }
}
